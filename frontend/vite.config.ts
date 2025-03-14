import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths';
import { VitePWA } from 'vite-plugin-pwa';

// https://vitejs.dev/config/
export default defineConfig({
  build: {
    target: 'ES2022',
  },
  plugins: [
    tsconfigPaths(),
    react(),
    VitePWA({
      registerType: 'prompt',
      manifest: {
        name: 'Vite Mui React Typescript',
        short_name: 'Vite Mui React Typescript',
        description: 'Vite Mui React Typescript',
        display: 'standalone',
        theme_color: '#1976d2',
        background_color: '#ffff',
        scope: '/',
        start_url: '/',
        orientation: 'portrait',
        icons: [
          {
            src: '/icons/icon-192x192.png',
            sizes: '192x192',
            type: 'image/png',
            purpose: 'favicon',
          },
          {
            src: '/icons/icon-256x256.png',
            sizes: '256x256',
            type: 'image/png',
            purpose: 'any',
          },
          {
            src: '/icons/icon-384x384.png',
            sizes: '384x384',
            type: 'image/png',
            purpose: 'any maskable',
          },
          {
            src: '/icons/icon-512x512.png',
            sizes: '512x512',
            type: 'image/png',
            purpose: 'any maskable',
          },
        ],
        screenshots: [
          {
            src: '/screenshots/screenshot1.png',
            type: 'image/png',
            sizes: '305x672',
            form_factor: 'narrow',
          },
          {
            src: '/screenshots/screenshot2.png',
            type: 'image/png',
            sizes: '861x723',
            form_factor: 'wide',
          },
        ],
      },
      includeAssets: ['**/*'],
      workbox: {
        globPatterns: ['**/*.{js,css,html,png,jpg,svg,json}'],
        runtimeCaching: [
          {
            urlPattern: ({ url }) => {
              return url.origin === 'http://localhost:8081';
            },
            handler: 'CacheFirst',
            options: {
              cacheName: 'api',
              cacheableResponse: {
                statuses: [0, 200],
              },
              expiration: {
                maxAgeSeconds: 60 * 60 * 24 * 7,
              },
            },
          },
        ],
      },
    }),
  ],
});
