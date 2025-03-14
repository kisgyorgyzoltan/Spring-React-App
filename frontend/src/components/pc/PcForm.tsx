import {
  Alert,
  Box,
  Button,
  CircularProgress,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  Snackbar,
  TextField,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useCallback, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { CreatePrebuiltPC, PrebuiltPC } from '../../api/pcs.api';
import { useCreatePcMutation, useDeletePcMutation, useUpdatePcMutation } from '../../mutations/pcs.mutation';
import { PCPart } from '../../api/pcparts.api';

type Props = {
  create: boolean;
  data: PrebuiltPC | null | undefined;
  pcParts: PCPart[] | null | undefined;
  numberId: number;
};

export default function PcForm({ create, data, numberId, pcParts }: Props) {
  const [isError, setIsError] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  const [cpu, setCpu] = useState((data?.cpu.id || '').toString());
  const [gpu, setGpu] = useState((data?.gpu.id || '').toString());
  const [ram, setRam] = useState((data?.ram.id || '').toString());
  const [motherboard, setMotherboard] = useState((data?.motherboard.id || '').toString());
  const [psu, setPsu] = useState((data?.psu.id || '').toString());
  const [storage, setStorage] = useState((data?.storage.id || '').toString());
  const [price, setPrice] = useState(data?.price || 0);

  const navigate = useNavigate();
  const { mutateAsync: mutateCreate } = useCreatePcMutation();
  const { mutateAsync: mutateDelete, isSuccess: isDeletionSuccess } = useDeletePcMutation();
  const { mutateAsync: mutateUpdate } = useUpdatePcMutation();
  const { t } = useTranslation();

  const onDelete = useCallback(
    async (numberId: number) => {
      await mutateDelete(numberId);
      navigate('/pcs');
    },
    [mutateDelete, navigate],
  );

  useEffect(() => {
    if (isDeletionSuccess) {
      navigate('/');
    }
  }, [isDeletionSuccess, navigate]);

  return (
    <Box
      sx={{
        '& .MuiTextField-root': { m: 1, width: '25ch' },
        color: 'common.black',
      }}
    >
      <Snackbar
        open={isSuccess}
        autoHideDuration={4000}
        message={t('form.success')}
        onClose={() => setIsSuccess(false)}
      >
        <Alert severity="success" variant="filled" sx={{ width: '100%' }}>
          {t('form.success')}
        </Alert>
      </Snackbar>
      <Snackbar
        open={isError}
        autoHideDuration={4000}
        message={t('form.error.invalid')}
        onClose={() => setIsError(false)}
      >
        <Alert severity="error" variant="filled" sx={{ width: '100%' }}>
          {t('form.error.invalid')}
        </Alert>
      </Snackbar>
      <Box>
        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <InputLabel id="cpu-label">CPU</InputLabel>
          <Select
            name="cpu"
            labelId="cpu-label"
            id="cpu"
            value={cpu}
            onChange={(event: SelectChangeEvent) => setCpu(event.target.value)}
            autoWidth
            label="CPU"
          >
            {data ? (
              <MenuItem value={data.cpu.id}>{data.cpu.name}</MenuItem>
            ) : (
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
            )}

            {pcParts ? (
              pcParts.map(
                (part) =>
                  part.id !== data?.cpu.id && (
                    <MenuItem key={part.id} value={part.id}>
                      {part.name}
                    </MenuItem>
                  ),
              )
            ) : (
              <CircularProgress />
            )}
          </Select>
        </FormControl>

        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <InputLabel id="gpu-label">GPU</InputLabel>
          <Select
            name="gpu"
            labelId="gpu-label"
            id="gpu"
            value={gpu}
            onChange={(event: SelectChangeEvent) => setGpu(event.target.value)}
            autoWidth
            label="GPU"
          >
            {data ? (
              <MenuItem value={data.gpu.id}>{data.gpu.name}</MenuItem>
            ) : (
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
            )}

            {pcParts ? (
              pcParts.map(
                (part) =>
                  part.id !== data?.gpu.id && (
                    <MenuItem key={part.id} value={part.id}>
                      {part.name}
                    </MenuItem>
                  ),
              )
            ) : (
              <CircularProgress />
            )}
          </Select>
        </FormControl>

        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <InputLabel id="ram-label">RAM</InputLabel>
          <Select
            name="ram"
            labelId="ram-label"
            id="ram"
            value={ram}
            onChange={(event: SelectChangeEvent) => setRam(event.target.value)}
            autoWidth
            label="RAM"
          >
            {data ? (
              <MenuItem value={data.ram.id}>{data.ram.name}</MenuItem>
            ) : (
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
            )}

            {pcParts ? (
              pcParts.map(
                (part) =>
                  part.id !== data?.ram.id && (
                    <MenuItem key={part.id} value={part.id}>
                      {part.name}
                    </MenuItem>
                  ),
              )
            ) : (
              <CircularProgress />
            )}
          </Select>
        </FormControl>
      </Box>
      <Box>
        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <InputLabel id="Motherboard-label">Motherboard</InputLabel>
          <Select
            name="motherboard"
            labelId="motherboard-label"
            id="motherboard"
            value={motherboard}
            onChange={(event: SelectChangeEvent) => setMotherboard(event.target.value)}
            autoWidth
            label={t('form.motherboard')}
          >
            {data ? (
              <MenuItem value={data.motherboard.id}>{data.motherboard.name}</MenuItem>
            ) : (
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
            )}

            {pcParts ? (
              pcParts.map(
                (part) =>
                  part.id !== data?.motherboard.id && (
                    <MenuItem key={part.id} value={part.id}>
                      {part.name}
                    </MenuItem>
                  ),
              )
            ) : (
              <CircularProgress />
            )}
          </Select>
        </FormControl>
        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <InputLabel id="psu-label">PSU</InputLabel>
          <Select
            name="psu"
            labelId="psu-label"
            id="psu"
            value={psu}
            onChange={(event: SelectChangeEvent) => setPsu(event.target.value)}
            autoWidth
            label="PSU"
          >
            {data ? (
              <MenuItem value={data.psu.id}>{data.psu.name}</MenuItem>
            ) : (
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
            )}

            {pcParts ? (
              pcParts.map(
                (part) =>
                  part.id !== data?.psu.id && (
                    <MenuItem key={part.id} value={part.id}>
                      {part.name}
                    </MenuItem>
                  ),
              )
            ) : (
              <CircularProgress />
            )}
          </Select>
        </FormControl>
        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <InputLabel id="storage-label">Storage</InputLabel>
          <Select
            name="storage"
            labelId="storage-label"
            id="storage"
            value={storage}
            onChange={(event: SelectChangeEvent) => setStorage(event.target.value)}
            autoWidth
            label={t('form.storage')}
          >
            {data ? (
              <MenuItem value={data.storage.id}>{data.storage.name}</MenuItem>
            ) : (
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
            )}

            {pcParts ? (
              pcParts.map(
                (part) =>
                  part.id !== data?.storage.id && (
                    <MenuItem key={part.id} value={part.id}>
                      {part.name}
                    </MenuItem>
                  ),
              )
            ) : (
              <CircularProgress />
            )}
          </Select>
        </FormControl>
      </Box>
      <Box>
        <FormControl sx={{ m: 1, minWidth: 80 }}>
          <TextField
            label={t('form.price')}
            name="price"
            id="price"
            value={price}
            onChange={(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
              setPrice(Number(event.target.value));
            }}
          />
        </FormControl>
      </Box>
      <Button
        variant="contained"
        type="button"
        onClick={async () => {
          if (pcParts === null || pcParts === undefined) {
            setIsError(true);
            return;
          }
          if (!cpu || !gpu || !ram || !motherboard || !psu || !storage) {
            setIsError(true);
            return;
          }
          const values = {
            id: numberId || -1,
            cpu: pcParts.find((part) => part.id === Number(cpu)),
            gpu: pcParts.find((part) => part.id === Number(gpu)),
            ram: pcParts.find((part) => part.id === Number(ram)),
            motherboard: pcParts.find((part) => part.id === Number(motherboard)),
            psu: pcParts.find((part) => part.id === Number(psu)),
            storage: pcParts.find((part) => part.id === Number(storage)),
            price: price,
          } as CreatePrebuiltPC;
          if (create) {
            const newPc = await mutateCreate(values);
            setIsSuccess(true);
            navigate(`/pcs/details/${newPc.id}`);
          } else {
            await mutateUpdate(values);
            setIsSuccess(true);
            navigate(`/pcs/details/${values.id}`);
          }
        }}
      >
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
  );
}
