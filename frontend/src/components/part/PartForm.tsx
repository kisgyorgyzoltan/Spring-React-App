import { Box, TextField, Button, CircularProgress, Alert } from '@mui/material';
import * as yup from 'yup';
import { useFormik } from 'formik';
import { PCPart, UpdatePcPart } from '../../api/pcparts.api';
import { useNavigate } from 'react-router-dom';
import { useCallback, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import {
  useCreatePcPartMutation,
  useUpdatePcPartMutation,
  useDeletePcPartMutation,
} from '../../mutations/pcparts.mutation';

type Props = {
  create: boolean;
  data: PCPart | null | undefined;
  numberId: number;
};

export default function PartForm({ create, data, numberId }: Props) {
  const navigate = useNavigate();
  const { mutateAsync: mutateCreate } = useCreatePcPartMutation();
  const { mutateAsync: mutateDelete, isSuccess: isDeletionSuccess } = useDeletePcPartMutation();
  const { mutateAsync: mutateUpdate } = useUpdatePcPartMutation();
  const { t } = useTranslation();

  const validationSchema = yup.object({
    name: yup.string().required(t('form.validation.name')),
    producer: yup.string().required(t('form.validation.producer')),
    type: yup.string().required(t('form.validation.type')),
    price: yup.number().required(t('form.validation.price')).min(1, 'Minimum price is 1').max(1000, 'Max price 1000'),
    weight: yup.number().required(t('form.validation.weight')).min(1, 'Minimum price 1').max(1000, 'Max price 1000'),
  });

  const onDelete = useCallback(
    async (numberId: number) => {
      await mutateDelete(numberId);
      navigate('/');
    },
    [mutateDelete, navigate],
  );

  useEffect(() => {
    if (isDeletionSuccess) {
      navigate('/');
    }
  }, [isDeletionSuccess, navigate]);

  const onSubmit = useCallback(
    async (values: UpdatePcPart) => {
      if (create) {
        const newPcPart = await mutateCreate(values);
        navigate(`/parts/details/${newPcPart.id}`);
      } else {
        await mutateUpdate(values);
        navigate(`/parts/details/${values.id}`);
      }
    },
    [create, mutateCreate, mutateUpdate, navigate],
  );

  const initialValues = create
    ? ({
        name: '',
        producer: '',
        type: '',
        price: 1,
        weight: 0,
      } as PCPart)
    : (data as PCPart);

  const myForm = useFormik({
    initialValues: initialValues,
    onSubmit: onSubmit,
    validationSchema: validationSchema,
    enableReinitialize: true,
  });

  if (!create && !data) {
    return <Alert severity="error">{t('form.error.notFound')}</Alert>;
  } else if (myForm.isSubmitting) {
    return <CircularProgress />;
  } else
    return (
      <form onSubmit={myForm.handleSubmit}>
        <Box
          sx={{
            '& .MuiTextField-root': { m: 1, width: '25ch' },
          }}
        >
          <div>
            <TextField
              id="name"
              name="name"
              label={t('form.name')}
              value={myForm.values.name}
              onChange={myForm.handleChange}
              onBlur={myForm.handleBlur}
              error={myForm.touched.name && Boolean(myForm.errors.name)}
            />
            <TextField
              id="producer"
              name="producer"
              label={t('form.producer')}
              value={myForm.values.producer}
              onChange={myForm.handleChange}
              onBlur={myForm.handleBlur}
              error={myForm.touched.producer && Boolean(myForm.errors.producer)}
            />
            <TextField
              id="type"
              name="type"
              label={t('form.type')}
              value={myForm.values.type}
              onChange={myForm.handleChange}
              onBlur={myForm.handleBlur}
              error={myForm.touched.type && Boolean(myForm.errors.type)}
            />
          </div>
          <div>
            <TextField
              id="price"
              name="price"
              label={t('form.price')}
              type="number"
              value={myForm.values.price}
              onChange={myForm.handleChange}
              onBlur={myForm.handleBlur}
              error={myForm.touched.price && Boolean(myForm.errors.price)}
            />
            <TextField
              id="weight"
              name="weight"
              label={t('form.weight')}
              type="number"
              value={myForm.values.weight}
              onChange={myForm.handleChange}
              onBlur={myForm.handleBlur}
              error={myForm.touched.weight && Boolean(myForm.errors.weight)}
            />
          </div>
          <Button variant="contained" type="submit">
            {create ? t('form.create') : t('form.update')}
          </Button>
          {!create && (
            <Button
              onClick={() => onDelete(numberId)}
              variant="contained"
              type="button"
              sx={{
                backgroundColor: 'red',
                color: 'white',
                ml: 1,
              }}
            >
              {t('form.delete')}
            </Button>
          )}
        </Box>
      </form>
    );
}
