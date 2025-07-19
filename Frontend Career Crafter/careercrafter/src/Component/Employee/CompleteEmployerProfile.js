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
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const CompleteEmployerProfile = () => {
  const [formData, setFormData] = useState({});
  const [userId, setUserId] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    const init = async () => {
      if (!token) {
        setError("Token not found.");
        setLoading(false);
        return;
      }

      try {
        const decoded = jwtDecode(token);
        const email = decoded.sub;

       
        const userRes = await axios.get(`http://localhost:9001/user/by-email/${email}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        const fetchedUserId = userRes.data.id;
        setUserId(fetchedUserId);

        
        try {
          await axios.get(`http://localhost:9001/employee/user/${fetchedUserId}`, {
            headers: { Authorization: `Bearer ${token}` },
          });

          
          navigate("/EmployerDashboard");
        } catch (err) {
          if (err.response.status === 404) {
            
            setLoading(false);
          } else {
            setError("Error checking profile.");
            setLoading(false);
          }
        }
      } catch (err) {
        console.error("Error during initialization:", err);
        setError("Something went wrong during setup.");
        setLoading(false);
      }
    };

    init();
  }, [token, navigate]);

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    setError(null);

    if (!/^[0-9]{10}$/.test(formData.contactNumber)) {
      setError("Contact number must be exactly 10 digits.");
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
      await axios.post("http://localhost:9001/employee/add", payload, {
        headers: { Authorization: `Bearer ${token}` },
      });

      alert("Employer profile completed successfully!");
      navigate("/EmployerDashboard");
    } catch (err) {
      console.error("Error saving employer profile:", err);
      setError(err.response?.data?.message || "Failed to save profile.");
    }
  };

  if (loading) {
    return (
      <Container style={{ marginTop: "6em" }}>
        <Message icon>
          <Icon name="circle notched" loading />
          <Message.Content>
            <Message.Header>Loading...</Message.Header>
            Please wait while we verify your account.
          </Message.Content>
        </Message>
      </Container>
    );
  }

  return (
    <Container style={{ marginTop: "6em", maxWidth: 700 }}>
      <Segment padded raised>
        <Header as="h2" icon textAlign="center" color="blue">
          <Icon name="building" circular />
          <Header.Content>Complete Employer Profile</Header.Content>
        </Header>

        {error && (
          <Message negative>
            <Message.Header>Error</Message.Header>
            <p>{error}</p>
          </Message>
        )}

        <Form onSubmit={handleSubmit}>
          <Form.Input
            label="Company Name"
            name="companyName"
            value={formData.companyName}
            onChange={handleChange}
            required
          />
          <Form.TextArea
            label="Company Details"
            name="companyDetails"
            value={formData.companyDetails}
            onChange={handleChange}
            required
          />
          <Form.Input
            label="Contact Number"
            name="contactNumber"
            value={formData.contactNumber}
            onChange={handleChange}
            placeholder="10-digit mobile number"
            required
          />
          <Form.Input
            label="Address"
            name="address"
            value={formData.address}
            onChange={handleChange}
            required
          />

          <Button
            color="green"
            fluid
            size="large"
            type="submit"
            disabled={!userId}
          >
            Save & Go to Dashboard
          </Button>
        </Form>
      </Segment>
    </Container>
  );
};

export default CompleteEmployerProfile;
