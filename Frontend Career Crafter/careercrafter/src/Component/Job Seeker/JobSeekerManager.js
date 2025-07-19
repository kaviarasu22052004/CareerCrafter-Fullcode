import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import {
  Grid,
  Menu,
  Segment,
  Header,
  Icon,
  Button,
  Form,
  Card,
  Loader,
  Image,
  Divider,
} from "semantic-ui-react";

const JobSeekerManager = () => {
  const [jobSeeker, setJobSeeker] = useState(null);
  const [formData, setFormData] = useState({});
  const [editing, setEditing] = useState(false);
  const [activeItem, setActiveItem] = useState("profile");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
  let jobSeekerEmail = null;

  if (token) {
    try {
      const decoded = jwtDecode(token);
      jobSeekerEmail = decoded.sub;
    } catch (error) {
      console.error("Invalid token", error);
    }
  }

  useEffect(() => {
    if (jobSeekerEmail) {
      axios
        .get(`http://localhost:9001/jobseeker/by-email/${jobSeekerEmail}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          setJobSeeker(res.data);
          setFormData(res.data);
        })
        .catch((err) => console.error("Error fetching job seeker:", err));
    }
  }, [jobSeekerEmail, token]);

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleUpdate = () => {
    axios
      .put(`http://localhost:9001/jobseeker/update/${jobSeeker.jobSeekerId}`, formData, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        alert("Profile updated successfully!");
        setEditing(false);
        setJobSeeker(formData);
      })
      .catch((err) => console.error("Error updating profile:", err));
  };

  const handleMenuClick = (item) => {
    setActiveItem(item);
    if (item === "applyjob") navigate("/applyjob");
    if (item === "uploadresume") navigate("/upload-resume");
    if (item === "applicationstatus") navigate("/jobseeker/application-status");
    if (item === "editprofile") setEditing(true);
    if (item === "profile") setEditing(false);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  if (!jobSeeker) return <Loader active inline="centered" content="Loading..." />;

  return (
    <div style={{ height: "100vh", overflow: "hidden", fontFamily: "Segoe UI" }}>
      {/* Top Navbar */}
      <Menu inverted fixed="top" style={{ height: "3.5em", zIndex: 1000 }}>
        <Menu.Item header>
          <Icon name="briefcase" />
          CareerCrafter
        </Menu.Item>
        <Menu.Menu position="right">
          <Menu.Item onClick={handleLogout}>
            <Icon name="sign-out" />
            Logout
          </Menu.Item>
        </Menu.Menu>
      </Menu>

      <div
        style={{
          position: "fixed",
          top: "3.5em",
          bottom: 0,
          left: 0,
          width: "17em",
          background: "#f9fafb",
          overflowY: "auto",
          padding: "1em",
          boxShadow: "2px 0 5px rgba(0,0,0,0.1)",
        }}
      >
        <Segment textAlign="center" basic>
          <Image
            src="JobSeeker.jpg"
            size="small"
            circular
            centered
          />
          <Header as="h4" style={{ marginTop: "0.5em" }}>{jobSeeker.name}</Header>
        </Segment>
        <Divider />
        <Menu fluid vertical secondary pointing>
          <Menu.Item
            name="View Profile"
            active={activeItem === "profile"}
            onClick={() => handleMenuClick("profile")}
          >
            <Icon name="user" /> View Profile
          </Menu.Item>
          <Menu.Item
            name="Edit Profile"
            active={activeItem === "editprofile"}
            onClick={() => handleMenuClick("editprofile")}
          >
            <Icon name="edit" /> Edit Profile
          </Menu.Item>
          <Menu.Item
            name="Apply for Job"
            active={activeItem === "applyjob"}
            onClick={() => handleMenuClick("applyjob")}
          >
            <Icon name="briefcase" /> Apply for Job
          </Menu.Item>
          <Menu.Item
            name="Upload Resume"
            active={activeItem === "uploadresume"}
            onClick={() => handleMenuClick("uploadresume")}
          >
            <Icon name="upload" /> Upload Resume
          </Menu.Item>
          <Menu.Item
            name="Application Status"
            active={activeItem === "applicationstatus"}
            onClick={() => handleMenuClick("applicationstatus")}
          >
            <Icon name="file alternate" /> Application Status
          </Menu.Item>
        </Menu>
      </div>

      
      <div
        style={{
          marginLeft: "17em",
          padding: "5em 2em 2em",
          height: "100%",
          overflowY: "auto",
          background: "linear-gradient(to right, #edf2f7, #e0f7fa)",
        }}
      >
        <Segment raised style={{ borderRadius: "1em" }}>
          <Header as="h2" color="blue">
            <Icon name="dashboard" />
            <Header.Content>Job Seeker Dashboard</Header.Content>
          </Header>

          {!editing ? (
            <Card fluid>
              <Card.Content>
                <Card.Header>{jobSeeker.name}</Card.Header>
                <Card.Meta>{jobSeeker.education}</Card.Meta>
                <Card.Description>
                  <p><strong>Experience:</strong> {jobSeeker.experience}</p>
                  <p><strong>Skills:</strong> {jobSeeker.skill}</p>
                  <p><strong>Mobile:</strong> {jobSeeker.mobileNumber}</p>
                  <p><strong>Address:</strong> {jobSeeker.address}</p>
                  <p><strong>Date of Birth:</strong> {jobSeeker.dob}</p>
                </Card.Description>
              </Card.Content>
            </Card>
          ) : (
            <Form onSubmit={handleUpdate}>
              <Form.Group widths="equal">
                <Form.Input
                  label="Name"
                  name="name"
                  value={formData.name || ""}
                  onChange={handleChange}
                  required
                />
                <Form.Input
                  label="Education"
                  name="education"
                  value={formData.education || ""}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group widths="equal">
                <Form.Input
                  label="Experience"
                  name="experience"
                  value={formData.experience || ""}
                  onChange={handleChange}
                />
                <Form.Input
                  label="Skills"
                  name="skill"
                  value={formData.skill || ""}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group widths="equal">
                <Form.Input
                  label="Mobile Number"
                  name="mobileNumber"
                  value={formData.mobileNumber || ""}
                  onChange={handleChange}
                />
                <Form.Input
                  label="Address"
                  name="address"
                  value={formData.address || ""}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Input
                label="Date of Birth"
                type="date"
                name="dob"
                value={formData.dob || ""}
                onChange={handleChange}
              />

              <Button color="blue" type="submit" icon labelPosition="left">
                <Icon name="save" />
                Save Changes
              </Button>
              <Button color="grey" onClick={() => setEditing(false)} icon labelPosition="left">
                <Icon name="cancel" />
                Cancel
              </Button>
            </Form>
          )}
        </Segment>
      </div>
    </div>
  );
};

export default JobSeekerManager;
