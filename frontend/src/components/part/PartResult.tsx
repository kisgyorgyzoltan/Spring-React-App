import {
  Button,
  Box,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Divider,
  Slider,
  TextField,
  Typography,
} from '@mui/material';
import { Link } from 'react-router-dom';
import InboxIcon from '@mui/icons-material/Inbox';
import { PCPart } from '../../api/pcparts.api';
import { useTranslation } from 'react-i18next';
import { useCallback } from 'react';

type PartResultProps = {
  items: PCPart[];
  setQPrice: (value: number[]) => void;
  setQProducer: (value: string) => void;
  setQType: (value: string) => void;
  qPrice: number[];
  refetch: () => void;
};

export default function PartResult({ items, setQPrice, setQProducer, setQType, qPrice, refetch }: PartResultProps) {
  const { t } = useTranslation();

  const handleSliderChange = useCallback(
    (_event: Event, newValue: number | number[]) => {
      setQPrice(newValue as number[]);
    },
    [setQPrice],
  );

  const handleProducerChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setQProducer(event.target.value);
    },
    [setQProducer],
  );

  const handleTypeChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setQType(event.target.value);
    },
    [setQType],
  );

  return (
    <>
      <Box sx={{ marginLeft: 1, borderRadius: 5, border: 1, borderColor: 'grey', padding: 2 }}>
        <Typography variant="h5" gutterBottom component="div">
          {t('list.filters')}
        </Typography>
        <TextField
          label={t('form.producer')}
          variant="standard"
          onChange={handleProducerChange}
          sx={{
            marginRight: 2,
          }}
        />
        <TextField label={t('form.type')} variant="standard" onChange={handleTypeChange} />
        <Box sx={{ width: 300, padding: 2, marginTop: 1 }}>
          <Typography gutterBottom>{t('form.price')}</Typography>
          <Slider
            getAriaLabel={() => t('list.priceRange')}
            value={qPrice}
            onChange={handleSliderChange}
            min={1}
            max={1000}
            valueLabelDisplay="on"
            sx={{
              marginTop: 4,
            }}
          />
        </Box>
        <Button
          onClick={() => {
            if (refetch) {
              refetch();
            }
          }}
          variant="contained"
        >
          {t('list.filter')}
        </Button>
      </Box>
      <Link to="/create/part">
        <Button
          sx={{
            color: 'common.black',
          }}
        >
          {t('list.add')}
        </Button>
      </Link>
      <Box sx={{ width: '100%' }}>
        <List>
          {items &&
            items.map((pcPart) => (
              <Link to={`/parts/details/${pcPart.id}`} key={pcPart.id}>
                <ListItem disablePadding>
                  <ListItemButton>
                    <ListItemIcon>
                      <InboxIcon
                        color="primary"
                        sx={{
                          backgroundColor: 'secondary.main',
                        }}
                      />
                    </ListItemIcon>
                    <ListItemText
                      primary={pcPart.name}
                      color="primary"
                      sx={{
                        textDecoration: 'none',
                        color: 'common.black',
                      }}
                    />
                  </ListItemButton>
                </ListItem>
              </Link>
            ))}
        </List>
        <Divider />
      </Box>
    </>
  );
}
