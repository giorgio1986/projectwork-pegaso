const { createGlobPatternsForDependencies } = require('@nx/vue/tailwind');
const { join } = require('path');

/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: ['class'],
  content: [
    './src/**/*.{js,ts,html,vue,scss,css,jsx,tsx}',
    join(__dirname, 'libs/**/*.{html,ts,css,scss,vue}'),
    ...createGlobPatternsForDependencies(__dirname),
  ],
  plugins: [],
  theme: {
    extend: {
      width: {
        'f-screen': '100dvw',
      },
      height: {
        'f-screen': '100dvh',
      },
      colors: {
        primary: {
          DEFAULT: '#316b48',
          50:  '#f2f7f4',
          100: '#dbe9e0',
          200: '#b8d3c1',
          300: '#93bfa3',
          400: '#5f926f',
          500: '#316b48',
          600: '#29583c',
          700: '#204630',
          800: '#173424',
          900: '#0f2218',
          950: '#09160f',
        },
        secondary: {
          DEFAULT: '#1a415d',
          50:  '#f2f7fa',
          100: '#d8e5ee',
          200: '#b1cbdd',
          300: '#85aeca',
          400: '#4e7ea4',
          500: '#1a415d',
          600: '#16384f',
          700: '#112d40',
          800: '#0c2130',
          900: '#081521',
          950: '#040b12',
        },
        background: '#dbe1e7',
        surface: '#FFFFFF',
        text: '#1F2937',
      }
    },
  }
};
