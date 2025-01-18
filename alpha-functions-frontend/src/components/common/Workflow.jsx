import React from 'react';
import VerticalStepper from './VerticalStepper';
import Controls from './Controls';
import { steps } from '../../utils/steps';
import '../layout/sidebar/Sidebar.css';

function Sidebar({activeStep, handleNext, handleBack, handleReset,handleFinish}) {

  return (
    <>
      <div className="sidebar">
        <VerticalStepper activeStep={activeStep} steps={steps} />
      </div>
      <Controls activeStep={activeStep} steps={steps} handleBack={handleBack} handleNext={handleNext} handleReset={handleReset} handleFinish={handleFinish}/>
    </>
  );
}

export default Sidebar;