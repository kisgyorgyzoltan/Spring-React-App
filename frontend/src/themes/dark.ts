import { createTheme } from '@mui/material';

export const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#121212',
    },
    secondary: {
      main: '#fff',
    },
    background: {
      default: 'rgb(192,192,192)',
    },
    text: {
      primary: 'rgb(0, 0, 0)',
      secondary: 'rgb(0, 0, 0)',
    },
  },
});
