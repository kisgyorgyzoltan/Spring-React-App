import { openDB } from 'idb';

export const db = await openDB('projekt', 1, {
  upgrade(db) {
    db.createObjectStore('pcparts', { keyPath: 'id' });
    db.createObjectStore('pcparts-detail', { keyPath: 'id' });
    db.createObjectStore('pcs', { keyPath: 'id' });
    db.createObjectStore('pcs-detail', { keyPath: 'id' });
  },
});
