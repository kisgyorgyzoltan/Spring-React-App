import { useQuery } from '@tanstack/react-query';
import { fetchPc, fetchPcs } from '../api/pcs.api';

export function usePcs() {
  return useQuery({
    queryKey: ['pcs'],
    queryFn: () => fetchPcs(),
  });
}

export function usePc(id: number) {
  return useQuery({
    queryKey: ['pc', id],
    queryFn: () => fetchPc(id),
  });
}
