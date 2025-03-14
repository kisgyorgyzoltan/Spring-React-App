import { Alert, CircularProgress } from '@mui/material';
import { useTranslation } from 'react-i18next';
import PcResult from './PcResult';
import { usePcs } from '../../query/pcs.query';

export default function PcListPage() {
  const { data, isLoading, isError, error } = usePcs();
  const { t } = useTranslation();

  const result = <PcResult items={data ?? []} />;
  return (
    <>
      {isError && <Alert severity="error">{`${t('error.error')}: ${error?.message}`}</Alert>}
      {isLoading ? <CircularProgress /> : result}
    </>
  );
}
