import React, { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import {
  Form,
  Button,
  Container,
  Header,
  Message,
  Dropdown,
  Segment,
  Icon,
  Grid,
  Image,
} from "semantic-ui-react";

const roleOptions = [
  { key: "jobseeker", text: "Job Seeker", value: "ROLE_JOBSEEKER" },
  { key: "employer", text: "Employer", value: "ROLE_EMPLOYER" },
  { key: "admin", text: "Admin", value: "ADMIN" },
];

const Signup = () => {
  const [formData, setFormData] = useState({});

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      await axios.post("http://localhost:9001/user/register", formData);

      const role = formData.role;
      if (role === "ROLE_EMPLOYER") navigate("/login");
      else if (role === "ROLE_JOBSEEKER") navigate("/login");
      else if (role === "ADMIN") navigate("/admin");
      else navigate("/");
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed.");
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "linear-gradient(to right, #e0f2fe, #f8fafc)",
        display: "flex",
        alignItems: "center",
      }}
    >
      <Grid stackable columns={2} style={{ width: "100%", minHeight: "100vh" }}>
        {/* Left Column - Image Section */}
        <Grid.Column width={8} textAlign="center" verticalAlign="middle" style={{ padding: "4em" }}>
          <Image
            src="pattern.png"
            size="large"
            centered
            rounded
          />
          <Header as="h2" style={{ marginTop: "1em", color: "#1e3a8a" }}>
            Join CareerCrafter
          </Header>
          <p style={{ fontSize: "1.2em", color: "#1e293b" }}>
            Build your professional future today. Sign up to get started!
          </p>
        </Grid.Column>

        <Grid.Column width={8} verticalAlign="middle">
          <Container style={{ maxWidth: 500 }}>
            <Segment padded="very" raised style={{ borderRadius: "1em", boxShadow: "0 4px 14px rgba(0,0,0,0.1)" }}>
              <Header as="h2" icon textAlign="center" color="blue">
                <Icon name="user plus" circular />
                <Header.Content>Create Your Account</Header.Content>
              </Header>

              {error && (
                <Message negative>
                  <Message.Header>Registration Error</Message.Header>
                  <p>{error}</p>
                </Message>
              )}

              <Form onSubmit={handleSubmit} size="large">
                <Form.Input
                  icon="user"
                  iconPosition="left"
                  label="Username"
                  placeholder="Enter username"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  required
                />
                <Form.Input
                  icon="mail"
                  iconPosition="left"
                  label="Email"
                  placeholder="Enter email"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
                <Form.Input
                  icon="lock"
                  iconPosition="left"
                  label="Password"
                  placeholder="Enter password"
                  name="password"
                  type="password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
                <Form.Field
                  control={Dropdown}
                  label="Select Role"
                  name="role"
                  fluid
                  selection
                  options={roleOptions}
                  value={formData.role}
                  onChange={handleChange}
                  required
                />

                <Button type="submit" color="blue" fluid size="large" style={{ marginTop: "1em" }}>
                  <Icon name="signup" /> Register
                </Button>
              </Form>

              <Message info style={{ marginTop: "1.5em" }}>
                Already have an account? <Link to="/login">Login here</Link>
              </Message>
            </Segment>
          </Container>
        </Grid.Column>
      </Grid>
    </div>
  );
};

export default Signup;
