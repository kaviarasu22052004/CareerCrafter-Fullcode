// src/Component/JobSeeker/UploadResume.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import {
  Container,
  Segment,
  Header,
  Form,
  Button,
  Message,
  Icon,
} from "semantic-ui-react";

const UploadResume = () => {
  const [formData, setFormData] = useState({
    filePath: "",
    uploadDate: new Date().toISOString().split("T")[0],
    jobSeekerId: null,
  });

  const [success, setSuccess] = useState(false);
  const [error, setError] = useState("");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token);
        const email = decoded?.sub;

        axios
          .get(`http://localhost:9001/jobseeker/by-email/${email}`, {
            headers: { Authorization: `Bearer ${token}` },
          })
          .then((res) => {
            setFormData((prev) => ({
              ...prev,
              jobSeekerId: res.data.jobSeekerId,
            }));
          })
          .catch((err) => {
            console.error("Job seeker fetch failed:", err);
            setError("Failed to load job seeker profile.");
          });
      } catch (e) {
        console.error("Token decode failed:", e);
        setError("Invalid token.");
      }
    }
  }, [token]);

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post("http://localhost:9001/resume/add", formData, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        setSuccess(true);
        setError("");
      })
      .catch((err) => {
        console.error("Error saving resume:", err);
        setSuccess(false);
        setError("Resume upload failed. Try again.");
      });
  };

  return (
    <div style={{ background: "#f9fafc", minHeight: "100vh", paddingTop: "6em" }}>
      <Container text>
        <Segment raised padded="very">
          <Header as="h2" icon textAlign="center" color="blue">
            <Icon name="upload" circular />
            <Header.Content>Upload Resume</Header.Content>
            <Header.Subheader>
              Enter your resume path and upload date
            </Header.Subheader>
          </Header>

          {success && (
            <Message success>
              <Message.Header>Success</Message.Header>
              <p>Your resume info has been uploaded.</p>
            </Message>
          )}

          {error && (
            <Message negative>
              <Message.Header>Error</Message.Header>
              <p>{error}</p>
            </Message>
          )}

          <Form onSubmit={handleSubmit}>
            <Form.Input
              label="File Path"
              name="filePath"
              placeholder="/your/path/resume.pdf"
              value={formData.filePath}
              onChange={handleChange}
              required
            />

            <Form.Input
              label="Upload Date"
              name="uploadDate"
              type="date"
              value={formData.uploadDate}
              onChange={handleChange}
              required
            />

            <Button color="blue" type="submit" fluid icon labelPosition="right">
              Save Resume Info
              <Icon name="check" />
            </Button>
          </Form>
        </Segment>
      </Container>
    </div>
  );
};

export default UploadResume;
