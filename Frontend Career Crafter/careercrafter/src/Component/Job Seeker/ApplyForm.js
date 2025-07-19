
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import {
  Container,
  Header,
  Segment,
  Form,
  Button,
  Message,
  Icon,
  Loader,
} from "semantic-ui-react";

const ApplyForm = () => {
  const { jobId } = useParams();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  let email = null;
  if (token) {
    try {
      email = jwtDecode(token)?.sub;
    } catch (err) {
      console.error("Bad token", err);
    }
  }

  const [jobSeekerId, setJobSeekerId] = useState(null);
  const [resumeId, setResumeId] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (email) {
      axios
        .get(`http://localhost:9001/jobseeker/by-email/${email}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          setJobSeekerId(res.data.jobSeekerId);
          setLoading(false);
        })
        .catch((err) => {
          console.error("Cannot fetch jobSeekerId:", err);
          setLoading(false);
        });
    }
  }, [email, token]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      appliedDate: new Date().toISOString().split("T")[0],
      status: "APPLIED",
      jobListingId: Number(jobId),
      jobSeekerId,
      resumeId: Number(resumeId),
    };

    axios
      .post("http://localhost:9001/application/apply", payload, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        alert("Application submitted successfully!");
        navigate("/jobseeker/application-status");
      })
      .catch((err) => {
        console.error("Application error:", err);
        alert("Something went wrong while applying.");
      });
  };

  return (
    <div style={{ background: "#f9fafc", minHeight: "100vh", paddingTop: "6em" }}>
      <Container text>
        <Segment raised padded="very">
          <Header as="h2" icon textAlign="center" color="blue">
            <Icon name="paper plane" />
            Apply for Job ID: {jobId}
            <Header.Subheader>
              Submit your application using your resume ID
            </Header.Subheader>
          </Header>

          {loading ? (
            <Loader active inline="centered" content="Loading your profile..." />
          ) : jobSeekerId ? (
            <Form onSubmit={handleSubmit}>
              <Form.Input
                label="Resume ID"
                placeholder="Enter your Resume ID"
                type="number"
                required
                value={resumeId}
                onChange={(e) => setResumeId(e.target.value)}
              />
              <Button color="blue" type="submit" fluid icon labelPosition="right">
                Submit Application <Icon name="check circle" />
              </Button>
            </Form>
          ) : (
            <Message warning>
              <Message.Header>Unable to load your profile</Message.Header>
              <p>Please log in and try again.</p>
            </Message>
          )}
        </Segment>
      </Container>
    </div>
  );
};

export default ApplyForm;
