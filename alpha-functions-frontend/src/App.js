import React, { useState } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import Workflow from './components/common/Workflow';
import Form from './components/form/Form';
import useStepper from './hooks/useStepper';
import { formConfigs } from './utils/formConfigs';
import './App.css';
import Functions from './components/functions/Functions';
import axios from 'axios';

const App = () => {
  const { activeStep, handleNext, handleBack, handleReset } = useStepper();

  const [formData, setFormData] = useState({});

  const submitFormData = async () => {
    const formDataObject = new FormData();
    console.log(formData.alphaFunctionFiles)
    formDataObject.append('alphaFunctionName', formData?.alphaFunctionName);
    formDataObject.append('alphaFunctionTimeout', formData?.alphaFunctionTimeout);
    formDataObject.append('alphaFunctionRuntime', formData?.alphaFunctionRuntime);
    formDataObject.append('alphaFunctionHandler', formData?.alphaFunctionHandler);
    formDataObject.append('alphaFunctionDescription', formData?.alphaFunctionDescription);
    formDataObject.append('alphaFunctionOperation', formData?.alphaFunctionOperation);
    formDataObject.append('alphaFunctionLanguage', formData?.alphaFunctionLanguage);
    formDataObject.append('alphaFunctionFiles', formData?.alphaFunctionFiles);
    formDataObject.append('repositoryUserName', formData?.repositoryUserName);
    formDataObject.append('minimumCpu', formData?.minimumCpu);
    formDataObject.append('maximumCpu', formData?.maximumCpu);
    formDataObject.append('minimumMemory', formData?.minimumMemory);
    formDataObject.append('maximumMemory', formData?.maximumMemory);
    formDataObject.append('envVariables', JSON.stringify(formData?.envVariables));
    formDataObject.append('parallelReplicas', formData?.parallelReplicas);
    formDataObject.append('parallelExecutionEnabled', formData?.parallelExecutionEnabled);
    formDataObject.append('maxRetries', formData?.maxRetries);
    console.log(formDataObject)
    try {
      const response = axios.post(`${process.env.REACT_APP_ALPHA_FUNCTIONS_BACKEND_URL}/alpha/function/create`, formDataObject, {
        headers: {
          'Content-Type': 'multipart/form-data; boundary=AaB03x',
          "Content-Disposition": "file"
        }
      }).then(res=>console.log(res.data));
      const result = await response.json();
      console.log('Success:', result);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleFinish = () => {
    console.log('formData', formData);
    submitFormData();
  }

  return (
    <BrowserRouter>
      <Navbar />
      <Route path="/" exact>
        <Functions />
      </Route>
      <Route path="/functions/create">
        <div className="main-content">
          <Workflow activeStep={activeStep} handleNext={handleNext} handleBack={handleBack} handleReset={handleReset} handleFinish={handleFinish} />
          <Form formConfig={formConfigs[activeStep]} activeStep={activeStep} handleNext={handleNext} handleBack={handleBack} handleReset={handleReset} handleFormData={setFormData} />
        </div>
      </Route>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
