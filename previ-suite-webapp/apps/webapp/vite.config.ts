/// <reference types='vitest' />
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig(() => ({
  root: __dirname,
  cacheDir: '../../node_modules/.vite/apps/webapp',
  server: {
    port: 4200,
    host: 'localhost',
    proxy: {
      ['/previ-suite-service-dev/api']: {
        target: 'http://localhost:8080',
        ws: false,
        secure: false,
        changeOrigin: true
      }
    }
  },
  preview: {
    port: 4300,
    host: 'localhost',
  },
  plugins: [vue()],
  build: {
    outDir: './dist',
    emptyOutDir: true,
    reportCompressedSize: true,
    commonjsOptions: {
      transformMixedEsModules: true,
    },
  },
}));
