import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createPc, updatePc, deletePcPart } from '../api/pcs.api';

export const useCreatePcMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createPc,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pcs'] });
    },
  });
};

export const useUpdatePcMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: updatePc,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pcs'] });
    },
  });
};

export const useDeletePcMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deletePcPart,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pcs'] });
    },
  });
};
