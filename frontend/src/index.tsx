import React from 'react';
import ReactDOM from 'react-dom/client';
import Root from './components/Root.tsx';
import { registerSW } from 'virtual:pwa-register';

import './i18n.ts';

const updateSW = registerSW({
  onNeedRefresh() {
    if (confirm('New content available. Reload?')) {
      updateSW(true);
    }
  },
});

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <Root />
  </React.StrictMode>,
);
