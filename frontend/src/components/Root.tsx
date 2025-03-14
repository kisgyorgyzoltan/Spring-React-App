import { Suspense, lazy, useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { queryClient } from '../query/common.query';
import { useTranslation } from 'react-i18next';
import { SelectChangeEvent, ThemeProvider } from '@mui/material';
import { getThemeByName } from '../themes/base';
import Cookies from 'js-cookie';

import Navbar from './Navbar';

const Home = lazy(() => import('./Home'));
const PartListPage = lazy(() => import('./part/PartListPage'));
const PcListPage = lazy(() => import('./pc/PcListPage'));
const NoMatch = lazy(() => import('./NoMatch'));
const PartDetailsPage = lazy(() => import('./part/PartDetailsPage'));
const PcDetailsPage = lazy(() => import('./pc/PcDetailsPage'));

const themeArray = [
  {
    name: 'blueTheme',
  },
  {
    name: 'darkTheme',
  },
  {
    name: 'lightTheme',
  },
];

function Root() {
  const [selectedThemeName, setSelectedThemeName] = useState(Cookies.get('selectedThemeName') || 'blueTheme');
  const [selectedTheme, setSelectedTheme] = useState(getThemeByName(Cookies.get('selectedThemeName') || 'blueTheme'));

  const handleChange = (event: SelectChangeEvent<string>) => {
    const newThemeName = event.target.value;
    setSelectedThemeName(newThemeName);
    setSelectedTheme(getThemeByName(newThemeName));
    Cookies.set('selectedThemeName', newThemeName, {
      expires: 365,
      sameSite: 'None',
      secure: true,
    });
  };

  const { t } = useTranslation();
  const fallbackElement = <p>{t('loading')}</p>;

  return (
    <ThemeProvider theme={selectedTheme}>
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <Navbar handleChange={handleChange} selectedThemeName={selectedThemeName} themeArray={themeArray} />
          <hr />
          <main>
            <Routes>
              <Route
                path="/"
                element={
                  <Suspense fallback={fallbackElement}>
                    <Home />
                  </Suspense>
                }
              />
              <Route
                path="/parts"
                element={
                  <Suspense fallback={fallbackElement}>
                    <PartListPage />
                  </Suspense>
                }
              />
              <Route
                path="/pcs"
                element={
                  <Suspense fallback={fallbackElement}>
                    <PcListPage />
                  </Suspense>
                }
              />
              <Route
                path="/create/part"
                element={
                  <Suspense fallback={fallbackElement}>
                    <PartDetailsPage create={true} />
                  </Suspense>
                }
              />
              <Route
                path="/create/pc"
                element={
                  <Suspense fallback={fallbackElement}>
                    <PcDetailsPage create={true} />
                  </Suspense>
                }
              />
              <Route
                path="/parts/details/:id"
                element={
                  <Suspense fallback={fallbackElement}>
                    <PartDetailsPage create={false} />
                  </Suspense>
                }
              />
              <Route
                path="/pcs/details/:id"
                element={
                  <Suspense fallback={fallbackElement}>
                    <PcDetailsPage create={false} />
                  </Suspense>
                }
              />
              <Route
                path="*"
                element={
                  <Suspense fallback={fallbackElement}>
                    <NoMatch />
                  </Suspense>
                }
              />
            </Routes>
          </main>
        </BrowserRouter>
        <ReactQueryDevtools initialIsOpen={false} />
      </QueryClientProvider>
    </ThemeProvider>
  );
}

export default Root;
