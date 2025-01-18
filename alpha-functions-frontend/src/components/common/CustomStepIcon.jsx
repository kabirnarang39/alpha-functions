import React from 'react';
import { styled } from '@mui/material/styles';
import RadioButtonUncheckedIcon from '@mui/icons-material/RadioButtonUnchecked';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';

const CustomStepIconRoot = styled('div')(({ theme, ownerState }) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  color: ownerState.active ? '#006ce0' : theme.palette.action.disabled,
  '& .circle': {
    width: 12,
    height: 12,
    borderRadius: '50%',
    border: `2px solid ${ownerState.active ? '#006ce0' : theme.palette.action.disabled}`,
    background: ownerState.active ? '#006ce0' : '#fff',
    color: ownerState.active ? '#fff' : '#fff',
  },
}));

function CustomStepIcon(props) {
  const { active, className } = props;

  return (
    <CustomStepIconRoot ownerState={{ active }} className={className}>
      {active ? (
        <RadioButtonCheckedIcon className="circle" />
      ) : (
        <RadioButtonUncheckedIcon className="circle" />
      )}
    </CustomStepIconRoot>
  );
}

export default CustomStepIcon;