import { useParams } from 'react-router-dom';
import { Alert, CircularProgress } from '@mui/material';
import { usePcPart } from '../../query/pcparts.query';
import PartForm from './PartForm';
import { useTranslation } from 'react-i18next';

type DetailsPageProps = {
  create: boolean;
};

export default function DetailsPage({ create }: DetailsPageProps) {
  const { id } = useParams();
  const numberId = Number(id);
  const { data, isLoading, isError, error } = usePcPart(numberId);
  const { t } = useTranslation();

  const form = <PartForm create={create} data={data} numberId={numberId} />;
  return (
    <>
      {isLoading ? <CircularProgress /> : form}
      {isError && <Alert severity="error">{`${t('error.error')}: ${error?.message}`}</Alert>}
    </>
  );
}
