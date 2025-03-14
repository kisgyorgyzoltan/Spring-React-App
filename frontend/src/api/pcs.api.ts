import { api } from './common.api';
import { PCPart } from './pcparts.api';
import { db } from '../db/db.common';

export type PrebuiltPC = {
  id: number;
  cpu: PCPart;
  gpu: PCPart;
  ram: PCPart;
  motherboard: PCPart;
  psu: PCPart;
  storage: PCPart;
  price: number;
};

export const fetchPcs = async (): Promise<PrebuiltPC[]> => {
  try {
    const response = await api.get<PrebuiltPC[]>('/prebuiltpcs');

    if (response && response.data) {
      const tx = db.transaction('pcs', 'readwrite');
      const store = tx.objectStore('pcs');
      response.data.forEach((pc) => {
        store.put(pc);
      });
    }

    if (!response || !response.data) {
      const tx = db.transaction('pcs', 'readonly');
      const store = tx.objectStore('pcs');
      const data = await store.getAll();
      console.log('Data from indexedDB: ', data);
      return data;
    }

    return response.data;
  } catch (error) {
    const tx = db.transaction('pcs', 'readonly');
    const store = tx.objectStore('pcs');
    const data = await store.getAll();
    console.log('Data from indexedDB: ', data);
    return data;
  }
};

export const fetchPc = async (id: number) => {
  if (!id) {
    return null;
  }
  try {
    const response = await api.get<PrebuiltPC>(`/prebuiltpcs/${id}`);

    if (!response || !response.data) {
      const tx = db.transaction('pcs-detail', 'readonly');
      const store = tx.objectStore('pcs-detail');
      const data = await store.get(id);
      console.log('Data from indexedDB: ', data);
      return data;
    }

    if (response && response.data) {
      const tx = db.transaction('pcs-detail', 'readwrite');
      const store = tx.objectStore('pcs-detail');
      store.put(response.data);
    }

    return response.data;
  } catch (error) {
    const tx = db.transaction('pcs-detail', 'readonly');
    const store = tx.objectStore('pcs-detail');
    const data = await store.get(id);
    console.log('Data from indexedDB: ', data);
    return data;
  }
};

export type UpdatePrebuiltPC = {
  id: number;
  cpu: PCPart;
  gpu: PCPart;
  ram: PCPart;
  motherboard: PCPart;
  psu: PCPart;
  storage: PCPart;
  price: number;
};

export const updatePc = async (values: UpdatePrebuiltPC): Promise<PrebuiltPC> => {
  console.log('updatePc: ');
  const url = `/prebuiltpcs/${values.id}`;
  const response = await api.put(url, values);

  if (response && response.data) {
    const tx = db.transaction('pcs', 'readwrite');
    const store = tx.objectStore('pcs');
    store.put(response.data);
  }

  return response.data;
};

export type CreatePrebuiltPC = {
  id: number;
  cpu: PCPart;
  gpu: PCPart;
  ram: PCPart;
  motherboard: PCPart;
  psu: PCPart;
  storage: PCPart;
  price: number;
};

export const createPc = async (values: CreatePrebuiltPC): Promise<PrebuiltPC> => {
  const response = await api.post(`/prebuiltpcs`, values);

  if (response && response.data) {
    const tx = db.transaction('pcs', 'readwrite');
    const store = tx.objectStore('pcs');
    store.put(response.data);
  }

  return response.data;
};

export const deletePcPart = async (id: number) => {
  await api.delete(`/prebuiltpcs/${id}`);

  const tx = db.transaction('pcs', 'readwrite');
  const store = tx.objectStore('pcs');
  store.delete(id);

  const tx2 = db.transaction('pcs-detail', 'readwrite');
  const store2 = tx2.objectStore('pcs-detail');
  store2.delete(id);

  return id;
};
