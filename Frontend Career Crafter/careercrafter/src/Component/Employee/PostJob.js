import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import {
  Container,
  Form,
  Button,
  Header,
  Segment,
  Icon,
  TextArea,
  Message,
} from "semantic-ui-react";

const PostJob = () => {
  const { employeeId } = useParams();

  const [formData, setFormData] = useState({
    title: "",
    description: "",
    qualifications: "",
    location: "",
    postDate: new Date().toISOString().split("T")[0],
    salary: "",
    employerId: "",
  });

  const [success, setSuccess] = useState(false);
  const [error, setError] = useState("");
  const token = localStorage.getItem("token");

  useEffect(() => {
    setFormData((prev) => ({
      ...prev,
      employerId: parseInt(employeeId),
    }));
  }, [employeeId]);

  const handleChange = (e, { name, value }) => {
    setFormData({ ...formData, [name]: value });
  };

  const handlePost = () => {
    setError("");
    setSuccess(false);

    axios
      .post("http://localhost:9001/joblisting/add", formData, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        setSuccess(true);
        setFormData((prev) => ({
          ...prev,
          title: "",
          description: "",
          qualifications: "",
          location: "",
          salary: "",
        }));
      })
      .catch((err) => {
        console.error("Error posting job:", err);
        setError("Failed to post the job. Please try again.");
      });
  };

  return (
    <Container text style={{ marginTop: "5em" }}>
      <Segment padded="very" raised>
        <Header as="h2" icon textAlign="center" color="teal">
          <Icon name="suitcase" circular />
          <Header.Content>Post a New Job</Header.Content>
        </Header>

        {success && (
          <Message success header="Success!" content="Job posted successfully." />
        )}
        {error && <Message negative header="Error" content={error} />}

        <Form onSubmit={handlePost}>
          <Form.Input
            label="Job Title"
            placeholder="Enter job title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            icon="briefcase"
            iconPosition="left"
            required
          />
          <Form.Field
            control={TextArea}
            label="Description"
            placeholder="Enter job description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            required
          />
          <Form.Input
            label="Qualifications"
            placeholder="Enter required qualifications"
            name="qualifications"
            value={formData.qualifications}
            onChange={handleChange}
            icon="graduation"
            iconPosition="left"
            required
          />
          <Form.Input
            label="Location"
            placeholder="Enter job location"
            name="location"
            value={formData.location}
            onChange={handleChange}
            icon="map marker alternate"
            iconPosition="left"
            required
          />
          <Form.Input
            label="Post Date"
            type="date"
            name="postDate"
            value={formData.postDate}
            onChange={handleChange}
            required
          />
          <Form.Input
            label="Salary"
            placeholder="Enter salary (in ₹)"
            name="salary"
            value={formData.salary}
            onChange={handleChange}
            icon="rupee"
            iconPosition="left"
            required
          />

          <Button color="teal" fluid size="large" type="submit" style={{ marginTop: "1em" }}>
            <Icon name="check circle" />
            Post Job
          </Button>
        </Form>
      </Segment>
    </Container>
  );
};

export default PostJob;
