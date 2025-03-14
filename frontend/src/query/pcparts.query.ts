import { useQuery } from '@tanstack/react-query';
import { fetchPcPart, fetchPcParts } from '../api/pcparts.api';

export function usePcParts(qProducer: string, qType: string, qMaxPrice: number, qMinPrice: number) {
  return useQuery({
    queryKey: ['pcparts'],
    queryFn: () => fetchPcParts(qProducer, qType, qMaxPrice, qMinPrice),
  });
}

export function usePcPart(id: number) {
  return useQuery({
    queryKey: ['pcparts', id],
    queryFn: () => fetchPcPart(id),
  });
}
