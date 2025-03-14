import { Button, Box, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Divider } from '@mui/material';
import { Link } from 'react-router-dom';
import InboxIcon from '@mui/icons-material/Inbox';
import { useTranslation } from 'react-i18next';
import { PrebuiltPC } from '../../api/pcs.api';

type PcResultProps = {
  items: PrebuiltPC[];
};

export default function PcResult({ items }: PcResultProps) {
  const { t } = useTranslation();
  return (
    <>
      <Link to="/create/pc">
        <Button
          sx={{
            color: 'common.black',
          }}
        >
          {t('list.addPc')}
        </Button>
      </Link>
      <Box>
        <List>
          {items &&
            items.map((pc) => (
              <Link to={`/pcs/details/${pc.id}`} key={pc.id}>
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
                    <ListItemText primary="PC" />
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
