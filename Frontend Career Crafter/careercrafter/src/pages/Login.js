import React, { useState } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useNavigate, Link } from "react-router-dom";
import {
  Grid,
  Form,
  Button,
  Container,
  Header,
  Message,
  Segment,
  Icon,
  Image,
} from "semantic-ui-react";

const Login = () => {
  const [credentials, setCredentials] = useState({});

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e, { name, value }) => {
    setCredentials({ ...credentials, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      const { data } = await axios.post("http://localhost:9001/api/auth/login", credentials);
      const { token } = data;
      console.log(data.status);
      const decoded = jwtDecode(token);
      const role = decoded.role;

      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      alert("Login successful!");

      if (role === "ROLE_JOBSEEKER") navigate("/complete-jobseeker-profile");
      else if (role === "ROLE_EMPLOYER") navigate("/complete-employer-profile");
      else navigate("/");
    } catch (err) {
      setError("Login failed");
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "linear-gradient(to right, #f0f9ff, #e0f2fe)",
        display: "flex",
        alignItems: "center",
      }}
    >
      <Grid stackable columns={2} divided style={{ width: "100%", minHeight: "100vh" }}>
        <Grid.Column width={8} textAlign="center" verticalAlign="middle" style={{ padding: "4em" }}>
          <Image
            src="Login image.png"
            size="large"
            centered
            rounded
          />
          <Header as="h2" style={{ marginTop: "1em", color: "#1e3a8a" }}>
            Welcome Back to CareerCrafter!
          </Header>
          <p style={{ fontSize: "1.2em", color: "#1e293b" }}>
            Your career journey starts here. Login to continue.
          </p>
        </Grid.Column>

        
        <Grid.Column width={8} verticalAlign="middle">
          <Container style={{ maxWidth: 450 }}>
            <Segment padded="very" raised style={{ borderRadius: "1em" }}>
              <Header as="h2" icon textAlign="center" color="blue">
                <Icon name="sign-in" circular />
                <Header.Content>Login to Your Account</Header.Content>
              </Header>

              {error && (
                <Message negative>
                  <Message.Header>Login Error</Message.Header>
                  <p>{error}</p>
                </Message>
              )}

              <Form size="large" onSubmit={handleSubmit}>
                <Form.Input
                  icon="mail"
                  iconPosition="left"
                  label="Email"
                  placeholder="Enter email"
                  name="email"
                  type="email"
                  value={credentials.email}
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
                  value={credentials.password}
                  onChange={handleChange}
                  required
                />

                <Button type="submit" color="blue" fluid size="large" style={{ marginTop: "1em" }}>
                  <Icon name="sign-in" /> Login
                </Button>
              </Form>

              <Message info style={{ marginTop: "1.5em" }}>
                Don't have an account? <Link to="/signup">Sign up here</Link>
              </Message>
            </Segment>
          </Container>
        </Grid.Column>
      </Grid>
    </div>
  );
};

export default Login;
