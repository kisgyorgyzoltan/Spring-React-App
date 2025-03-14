import { api } from './common.api';
import { db } from '../db/db.common';

export type PCPart = {
  id: number;
  name: string;
  producer: string;
  type: string;
  price: number;
  weight: number;
};

export type Response = PCPart[] | PCPart;

export const fetchPcParts = async (qProducer: string, qType: string, qMaxPrice: number, qMinPrice: number) => {
  try {
    const queryOk = qMaxPrice !== 0 || qMinPrice !== 0;
    const queryParams = new URLSearchParams({
      producer: qProducer,
      type: qType,
      maxPrice: qMaxPrice.toString(),
      minPrice: qMinPrice.toString(),
    });

    const url = queryOk ? `/pcparts?${queryParams.toString()}` : `/pcparts`;
    const response = await api.get<PCPart[]>(url);

    if (!response || !response.data) {
      console.log('No response from server');
      const tx = db.transaction('pcparts', 'readonly');
      const store = tx.objectStore('pcparts');
      const data = await store.getAll();
      console.log('Data from indexedDB: ', data);
      return data;
    }

    const tx = db.transaction('pcparts', 'readwrite');
    const store = tx.objectStore('pcparts');
    response.data.forEach((pcpart) => {
      store.put(pcpart);
    });

    return response.data;
  } catch (error) {
    const tx = db.transaction('pcparts', 'readonly');
    const store = tx.objectStore('pcparts');
    const data = await store.getAll();
    console.log('Data from indexedDB: ', data);
    return data;
  }
};

export const fetchPcPart = async (id: number) => {
  if (!id) {
    return null;
  }
  try {
    const response = await api.get<PCPart>(`/pcparts/${id}`);

    if (!response || !response.data) {
      const tx = db.transaction('pcparts-detail', 'readonly');
      const store = tx.objectStore('pcparts-detail');
      const data = await store.get(id);
      console.log('Data from indexedDB: ', data);
      return data;
    }

    // save to indexedDB
    const tx = db.transaction('pcparts-detail', 'readwrite');
    const store = tx.objectStore('pcparts-detail');
    store.put(response.data);

    return response.data;
  } catch (error) {
    const tx = db.transaction('pcparts-detail', 'readonly');
    const store = tx.objectStore('pcparts-detail');
    const data = await store.get(id);
    console.log('Data from indexedDB: ', data);
    return data;
  }
};

export type UpdatePcPart = {
  id: number;
  name: string;
  producer: string;
  type: string;
  price: number;
  weight: number;
};

export const updatePcPart = async (values: UpdatePcPart): Promise<PCPart> => {
  const response = await api.put(`/pcparts/${values.id}`, values);

  if (response && response.data) {
    const tx = db.transaction('pcparts', 'readwrite');
    const store = tx.objectStore('pcparts');
    store.put(response.data);
  }

  return response.data;
};

export type CreatePcPart = {
  name: string;
  producer: string;
  type: string;
  price: number;
  weight: number;
};

export const createPcPart = async (values: CreatePcPart): Promise<PCPart> => {
  const response = await api.post(`/pcparts`, values);

  if (response && response.data) {
    const tx = db.transaction('pcparts', 'readwrite');
    const store = tx.objectStore('pcparts');
    store.put(response.data);
  }

  return response.data;
};

export const deletePcPart = async (id: number) => {
  await api.delete(`/pcparts/${id}`);

  const tx = db.transaction('pcparts', 'readwrite');
  const store = tx.objectStore('pcparts');
  store.delete(id);

  const tx2 = db.transaction('pcparts-detail', 'readwrite');
  const store2 = tx2.objectStore('pcparts-detail');
  store2.delete(id);

  return id;
};
