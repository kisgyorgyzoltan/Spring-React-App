import { Theme } from '@mui/material';
import { darkTheme } from './dark';
import { lightTheme } from './light';
import { blueTheme } from './blue';

export function getThemeByName(theme: string): Theme {
  switch (theme) {
    case 'darkTheme':
      return darkTheme;
    case 'lightTheme':
      return lightTheme;
    case 'blueTheme':
      return blueTheme;
    default:
      return lightTheme;
  }
}
