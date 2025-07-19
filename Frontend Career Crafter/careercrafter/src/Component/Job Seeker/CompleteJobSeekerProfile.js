import React, { useState, useEffect } from "react";
import {
  Container,
  Form,
  Button,
  Header,
  Segment,
  Message,
  Icon,
} from "semantic-ui-react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const CompleteJobSeekerProfile = () => {
  const [formData, setFormData] = useState({});

  const [userId, setUserId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      setError("Token not found.");
      setLoading(false);
      return;
    }

    try {
      const decoded = jwtDecode(token);
      const email = decoded.sub;
      axios
        .get(`http://localhost:9001/jobseeker/by-email/${email}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then(() => {
          navigate("/JobSeekerManager");
        })
        .catch((err) => {
          if (err.response && err.response.status === 404) {
            axios
              .get(`http://localhost:9001/user/by-email/${email}`, {
                headers: { Authorization: `Bearer ${token}` },
              })
              .then((res) => {
                setUserId(res.data.id);
                setLoading(false);
              })
              .catch(() => {
                setError("User not found for email.");
                setLoading(false);
              });
          } else {
            setError("Error checking job seeker profile.");
            setLoading(false);
          }
        });
    } catch {
      setError("Invalid token.");
      setLoading(false);
    }
  }, [token, navigate]);

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    setError(null);

    if (!/^[0-9]{10}$/.test(formData.mobileNumber)) {
      setError("Mobile number must be exactly 10 digits.");
      return;
    }

    if (!userId) {
      setError("User ID is missing.");
      return;
    }

    const payload = {
      ...formData,
      userId: userId,
    };

    try {
      await axios.post("http://localhost:9001/jobseeker/add", payload, {
        headers: { Authorization: `Bearer ${token}` },
      });

      alert("Job Seeker profile completed successfully!");
      navigate("/JobSeekerManager");
    } catch (err) {
      console.error("Error saving job seeker profile:", err);
      setError(err.response?.data?.message || "Failed to save profile.");
    }
  };

  return (
    <Container style={{ marginTop: "6em", maxWidth: 700 }}>
      <Segment padded raised>
        <Header as="h2" icon textAlign="center" color="teal">
          <Icon name="user plus" circular />
          <Header.Content>Complete Job Seeker Profile</Header.Content>
        </Header>

        {loading ? (
          <Message icon>
            <Icon name="circle notched" loading />
            <Message.Content>
              <Message.Header>Fetching user data...</Message.Header>
            </Message.Content>
          </Message>
        ) : (
          <>
            {error && (
              <Message negative>
                <Message.Header>Error</Message.Header>
                <p>{error}</p>
              </Message>
            )}

            <Form onSubmit={handleSubmit}>
              <Form.Input
                label="Full Name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
              />
              <Form.Input
                label="Education"
                name="education"
                value={formData.education}
                onChange={handleChange}
              />
              <Form.Input
                label="Experience"
                name="experience"
                value={formData.experience}
                onChange={handleChange}
              />
              <Form.Input
                label="Skills"
                name="skill"
                value={formData.skill}
                onChange={handleChange}
              />
              <Form.Input
                label="Date of Birth"
                name="dob"
                type="date"
                value={formData.dob}
                onChange={handleChange}
              />
              <Form.Input
                label="Mobile Number"
                name="mobileNumber"
                value={formData.mobileNumber}
                onChange={handleChange}
                placeholder="10-digit mobile number"
                required
              />
              <Form.Input
                label="Address"
                name="address"
                value={formData.address}
                onChange={handleChange}
              />

              <Button
                color="teal"
                fluid
                size="large"
                type="submit"
                disabled={!userId}
              >
                Save & Go to Dashboard
              </Button>
            </Form>
          </>
        )}
      </Segment>
    </Container>
  );
};

export default CompleteJobSeekerProfile;
