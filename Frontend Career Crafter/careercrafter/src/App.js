// src/App.js
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Home from './pages/Home';
import JobSeekerManager from './Component/Job Seeker/JobSeekerManager';
import EmployerDashboard from './Component/Employee/Dashboard';
import PostJob from './Component/Employee/PostJob';
import PostedJob from './Component/Employee/PostedJob';
import ApplyJob from './Component/Job Seeker/ApplyJob';
import ApplyForm from './Component/Job Seeker/ApplyForm';
import Resume from './Component/Job Seeker/Resume';
import ApplicationApproval from "./Component/Employee/ApplicationApproval";
import ApplicationStatus from "./Component/Job Seeker/ApplicationStatus";
import CompleteEmployerProfile from "./Component/Employee/CompleteEmployerProfile";
import CompleteJobSeekerProfile from "./Component/Job Seeker/CompleteJobSeekerProfile";
function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/login" element={<Login />} />
      <Route path="/JobSeekerManager" element={<JobSeekerManager />} />
      <Route path="/EmployerDashboard" element={<EmployerDashboard />} />
      <Route path="/post-job/:employeeId" element={<PostJob />} />
      <Route path="/posted-jobs" element={<PostedJob />} />
      <Route path="/applyjob" element={<ApplyJob />} />
      <Route path="/apply/:jobId" element={<ApplyForm />} />
      <Route path="/upload-resume" element={<Resume />} />
     <Route path="/application-status/:jobListingId" element={<ApplicationApproval />} />
    <Route path="/jobseeker/application-status" element={<ApplicationStatus />} />
    <Route path="/complete-employer-profile" element={<CompleteEmployerProfile />} />
    <Route path="/complete-jobseeker-profile" element={<CompleteJobSeekerProfile />} />

    </Routes>
  );
}

export default App;
