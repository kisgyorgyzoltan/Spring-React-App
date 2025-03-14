import { useState } from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import { Box } from '@mui/system';
import { Link } from 'react-router-dom';
import { useTheme } from '@mui/material/styles';
import {
  List,
  ListItem,
  ListItemText,
  Collapse,
  useMediaQuery,
  Button,
  Typography,
  Toolbar,
  AppBar,
  MenuItem,
  Select,
  SelectChangeEvent,
} from '@mui/material';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import { useTranslation } from 'react-i18next';

interface Langs {
  [key: string]: string;
}

const lngs: Langs = {
  en: 'English',
  hu: 'Magyar',
  de: 'Deutsch',
};

type NavbarProps = {
  handleChange: (event: SelectChangeEvent<string>) => void;
  selectedThemeName: string;
  themeArray: { name: string }[];
};

export default function Navbar({ handleChange, selectedThemeName, themeArray }: NavbarProps) {
  const { t, i18n } = useTranslation();
  const small = useMediaQuery('(max-width:600px)');
  const full = useMediaQuery('(min-width:600px)');
  const theme = useTheme();

  const [open, setOpen] = useState(false);

  const handleClick = () => {
    setOpen(!open);
  };

  return (
    <>
      <AppBar
        position="static"
        sx={{
          backgroundColor: theme.palette.primary.main,
          color: theme.palette.secondary.main,
          textDecoration: 'none',
        }}
      >
        <Toolbar variant="dense">
          {small && (
            <>
              <List>
                <ListItem>
                  <Button onClick={handleClick}>
                    <MenuIcon />
                    {open ? <ExpandLess /> : <ExpandMore />}
                  </Button>
                  <Typography
                    variant="h6"
                    color={theme.palette.secondary.main}
                    onClick={() => {
                      setOpen(false);
                    }}
                  >
                    Lab 3
                  </Typography>
                </ListItem>
                <Collapse in={open} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    <Link to="/">
                      <ListItem>
                        <ListItemText primary={t('home.title')} />
                      </ListItem>
                    </Link>
                    <Link to="/parts">
                      <ListItem>
                        <ListItemText primary={t('list.parts')} />
                      </ListItem>{' '}
                    </Link>
                    <Link to="/pcs">
                      <ListItem>
                        <ListItemText primary={t('list.pcs')} />
                      </ListItem>{' '}
                    </Link>
                  </List>
                </Collapse>
              </List>
            </>
          )}

          {full && (
            <>
              <Typography variant="h6" color={theme.palette.secondary.main}>
                Lab 3
              </Typography>
              <Link to="/">
                <Button color="secondary">{t('home.title')}</Button>
              </Link>
              <Link to="/parts">
                <Button color="secondary">{t('list.parts')}</Button>
              </Link>
              <Link to="/pcs">
                <Button color="secondary">{t('list.pcs')}</Button>
              </Link>
              <Box sx={{ marginLeft: 'auto', display: 'flex', alignItems: 'center', textDecoration: 'none' }}>
                {Object.keys(lngs).map((lng) => (
                  <Button
                    key={lng}
                    color="secondary"
                    onClick={() => {
                      i18n.changeLanguage(lng);
                    }}
                    disabled={i18n.resolvedLanguage === lng}
                    sx={{ fontSize: '0.8rem', ml: 1 }}
                  >
                    {lngs[lng]}
                  </Button>
                ))}
                <Box>
                  <Select autoWidth value={selectedThemeName} onChange={handleChange}>
                    {themeArray.map((theme) => (
                      <MenuItem key={theme.name} value={theme.name}>
                        {theme.name}
                      </MenuItem>
                    ))}
                  </Select>
                </Box>
              </Box>
            </>
          )}
        </Toolbar>
      </AppBar>
    </>
  );
}
