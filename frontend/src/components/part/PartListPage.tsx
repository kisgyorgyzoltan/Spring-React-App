import { Alert, CircularProgress } from '@mui/material';
import { useTranslation } from 'react-i18next';
import PartResult from './PartResult';
import { usePcParts } from '../../query/pcparts.query';
import { useState } from 'react';

export default function PartListPage() {
  const [qPrice, setQPrice] = useState<number[]>([1, 1000]);
  const [qProducer, setQProducer] = useState<string>('');
  const [qType, setQType] = useState<string>('');
  const { data, isLoading, isError, error, refetch } = usePcParts(qProducer, qType, qPrice[1], qPrice[0]);

  const { t } = useTranslation();

  const result = (
    <PartResult
      items={data ?? []}
      setQPrice={setQPrice}
      setQProducer={setQProducer}
      setQType={setQType}
      qPrice={qPrice}
      refetch={refetch}
    />
  );
  return (
    <>
      {isError && <Alert severity="error">{`${t('error.error')}: ${error?.message}`}</Alert>}
      {isLoading ? <CircularProgress /> : result}
    </>
  );
}
