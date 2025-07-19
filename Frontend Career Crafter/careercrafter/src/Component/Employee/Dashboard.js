
import React, { useEffect, useState } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Segment,
  Header,
  Icon,
  Button,
  Form,
  Grid,
  Menu,
  Sidebar,
} from "semantic-ui-react";

const EmployerDashboard = () => {
  const [employer, setEmployer] = useState(null);
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({});
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token);
        const email = decoded?.sub;

        axios
          .get(`http://localhost:9001/employee/by-email/${email}`, {
            headers: { Authorization: `Bearer ${token}` },
          })
          .then((res) => {
            if (!res.data || !res.data.employeeId) {
              navigate("/complete-employer-profile");
            } else {
              setEmployer(res.data);
              setFormData(res.data);
            }
          })
          .catch(() => navigate("/complete-employer-profile"));
      } catch (err) {
        console.error("Invalid token:", err);
      }
    }
  }, [token, navigate]);

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleUpdate = () => {
    axios
      .put(
        `http://localhost:9001/employee/update/${employer?.employeeId}`,
        formData,
        { headers: { Authorization: `Bearer ${token}` } }
      )
      .then(() => {
        alert("Employer profile updated.");
        setEditing(false);
        setEmployer(formData);
      })
      .catch((err) => console.error("Update error:", err));
  };

  const handleNavigation = (path) => {
    navigate(path);
  };

  if (!employer) {
    return (
      <Container textAlign="center" style={{ marginTop: "10em" }}>
        <Header as="h3" icon>
          <Icon name="spinner" loading />
          Loading Employer Data...
        </Header>
      </Container>
    );
  }

  return (
    <div style={{ background: "#f9fafc", minHeight: "100vh", paddingTop: "6em" }}>
      <Menu fixed="top" inverted>
        <Menu.Item header>
          <Icon name="building outline" />
          CareerCrafter - Employer
        </Menu.Item>
        <Menu.Menu position="right">
          <Menu.Item>Welcome, {employer.companyName}</Menu.Item>
          <Menu.Item onClick={() => navigate("/")}>Logout</Menu.Item>
        </Menu.Menu>
      </Menu>

      <Sidebar.Pushable as={Segment} basic style={{ marginTop: "1em" }}>
        <Grid>
          <Grid.Row>
            <Grid.Column width={4}>
              <Menu fluid vertical tabular secondary>
                <Menu.Item
                  active={!editing}
                  onClick={() => setEditing(false)}
                >
                  <Icon name="user circle" /> Profile
                </Menu.Item>
                <Menu.Item
                  active={editing}
                  onClick={() => setEditing(true)}
                >
                  <Icon name="edit" /> Edit Profile
                </Menu.Item>
                <Menu.Item
                  onClick={() => handleNavigation(`/post-job/${employer.employeeId}`)}
                >
                  <Icon name="plus" /> Post New Job
                </Menu.Item>
                <Menu.Item onClick={() => handleNavigation("/posted-jobs")}>
                  <Icon name="list" /> View Listed Jobs
                </Menu.Item>
              </Menu>
            </Grid.Column>

            <Grid.Column stretched width={12}>
              <Segment raised>
                <Header as="h2" icon textAlign="center" color="blue">
                  <Icon name="briefcase" circular />
                  <Header.Content>Employer Dashboard</Header.Content>
                </Header>

                {!editing ? (
                  <div style={{ marginTop: "2em" }}>
                    <p><strong>Company Name:</strong> {employer.companyName}</p>
                    <p><strong>Details:</strong> {employer.companyDetails}</p>
                    <p><strong>Contact Number:</strong> {employer.contactNumber}</p>
                    <p><strong>Address:</strong> {employer.address}</p>
                    <Button icon labelPosition="left" primary onClick={() => setEditing(true)}>
                      <Icon name="edit" /> Edit Profile
                    </Button>
                  </div>
                ) : (
                  <Form onSubmit={handleUpdate}>
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
                      required
                    />
                    <Form.Input
                      label="Address"
                      name="address"
                      value={formData.address}
                      onChange={handleChange}
                      required
                    />
                    <Button color="green" icon labelPosition="left" type="submit">
                      <Icon name="save" /> Save Changes
                    </Button>
                    <Button color="grey" onClick={() => setEditing(false)}>
                      Cancel
                    </Button>
                  </Form>
                )}
              </Segment>
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </Sidebar.Pushable>
    </div>
  );
};

export default EmployerDashboard;
