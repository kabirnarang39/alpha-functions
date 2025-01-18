import React, { useEffect, useState } from 'react';
import { Grid, CircularProgress, Typography, Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import './Functions.css';  // Assuming you want to add custom styles here
import { Delete, DeleteOutline, Edit, RemoveRedEyeSharp } from '@mui/icons-material';

const Functions = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(`${process.env.REACT_APP_ALPHA_FUNCTIONS_BACKEND_URL}/alpha/function/read`)
      .then((response) => response.json())
      .then((data) => {
        setData(data?.data?.response);
        setLoading(false);
      })
      .catch((err) => {
        setError(err); 
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <div className="loading-container">
        <CircularProgress />
      </div>
    );
  }

  if (error) {
    return (
      <div className="error-container">
        <Typography color="error">Error fetching data: {error.message}</Typography>
      </div>
    );
  }

  const handleView = (id) => {
    alert(`Viewing function with id: ${id}`);
  };

  const handleEdit = (id) => {
    alert(`Editing function with id: ${id}`);
  };

  const handleDelete = (id) => {
    if (window.confirm(`Are you sure you want to delete function with id: ${id}?`)) {
      alert(`Deleting function with id: ${id}`);
    }
  };

  const columns = [
    {
        field: 'actions',
        headerName: '',
        width: 200,
        renderCell: (params) => {
          const { id } = params.row;
          return (
            <div style={{}}>
              <Button onClick={() => handleView(id)} color="info" size="small">
                <RemoveRedEyeSharp/>
              </Button>
              <Button onClick={() => handleEdit(id)} color="warning" size="small">
                <Edit/>
              </Button>
              <Button onClick={() => handleDelete(id)} color="error" size="small">
                <DeleteOutline/>
              </Button>
            </div>
          );
        }
      },
    { field: 'alphaName', headerName: 'Function Name', width: 200 },
    { field: 'version', headerName: 'Version', width: 120 },
    { field: 'runtime', headerName: 'Runtime', width: 150 },
    { field: 'language', headerName: 'Language', width: 150 },
    { field: 'handler', headerName: 'Handler', width: 200 },
    { field: 'description', headerName: 'Description', width: 300 },
    { field: 'isLatestVersion', headerName: 'Is Latest Version', width: 180, renderCell: (params) => (params.value ? 'Yes' : 'No') },
    { field: 'parallelExecutionEnabled', headerName: 'Parallel Execution Enabled', width: 250, renderCell: (params) => (params.value ? 'Yes' : 'No') },
    { field: 'parallelReplicas', headerName: 'Parallel Replicas', width: 180 },
    { field: 'timeout', headerName: 'Timeout', width: 120 },
    { field: 'minimumCpu', headerName: 'Min CPU', width: 120 },
    { field: 'maximumCpu', headerName: 'Max CPU', width: 120 },
    { field: 'minimumMemory', headerName: 'Min Memory', width: 120 },
    { field: 'maximumMemory', headerName: 'Max Memory', width: 120 },
    { field: 'envVariables', headerName: 'Env Variables', width: 250 },
    { field: 'maxRetries', headerName: 'Max Retries', width: 180 },
    { field: 'executed', headerName: 'Executed', width: 150, renderCell: (params) => (params.value ? 'Yes' : 'No') },
  ];

  // Format data for DataGrid
  const rows = data?.map((item, index) => ({
    id: index, // Adding a unique id field for the row
    alphaName: item.alphaName,
    version: item.version,
    runtime: item.runtime,
    language: item.language,
    handler: item.handler,
    description: item.description,
    isLatestVersion: item.isLatestVersion,
    parallelExecutionEnabled: item.parallelExecutionEnabled,
    parallelReplicas: item.parallelReplicas,
    timeout: item.timeout,
    minimumCpu: item.minimumCpu,
    maximumCpu: item.maximumCpu,
    minimumMemory: item.minimumMemory,
    maximumMemory: item.maximumMemory,
    envVariables: item.envVariables,
    maxRetries: item.maxRetries,
    executed: item.executed,
  }));

  return (
    <div className="functions-container">
      <div style={{ height: '100%', width: '100%' }}>
        <DataGrid
          rows={rows}
          columns={columns}
          pageSize={10}
          rowsPerPageOptions={[10]}
          disableSelectionOnClick
          loading={loading}
          getRowId={(row) => row.id}
        />
      </div>
    </div>
  );
};

export default Functions;
