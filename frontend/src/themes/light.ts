import { createTheme } from '@mui/material';

export const lightTheme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: 'rgb(255, 255, 255)',
    },
    secondary: {
      main: 'rgb(0, 0, 0)',
    },
    background: {
      default: '#fff',
    },
  },
});
