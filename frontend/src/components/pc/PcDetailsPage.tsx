import { useParams } from 'react-router-dom';
import { Alert, CircularProgress } from '@mui/material';
import PcForm from './PcForm';
import { useTranslation } from 'react-i18next';
import { usePc } from '../../query/pcs.query';
import { usePcParts } from '../../query/pcparts.query';

type PcDetailsPageProps = {
  create: boolean;
};

export default function PcDetailsPage({ create }: PcDetailsPageProps) {
  const { id } = useParams();
  const numberId = Number(id);
  const { data, isLoading, isError, error } = usePc(numberId);
  const { data: pcParts } = usePcParts('', '', 0, 0);
  const { t } = useTranslation();

  const form = <PcForm create={create} data={data} numberId={numberId} pcParts={pcParts} />;
  return (
    <>
      {isLoading ? <CircularProgress /> : form}
      {isError && <Alert severity="error">{`${t('error.error')}: ${error?.message}`}</Alert>}
    </>
  );
}
