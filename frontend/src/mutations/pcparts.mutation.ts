import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createPcPart, updatePcPart, deletePcPart } from '../api/pcparts.api';

export const useCreatePcPartMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createPcPart,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pcparts'] });
    },
  });
};

export const useUpdatePcPartMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: updatePcPart,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pcparts'] });
    },
  });
};

export const useDeletePcPartMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deletePcPart,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pcparts'] });
    },
  });
};
