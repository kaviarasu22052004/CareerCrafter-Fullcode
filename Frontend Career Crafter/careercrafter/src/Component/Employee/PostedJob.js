import React, { useEffect, useState } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Header,
  Card,
  Icon,
  Button,
  Message,
  Segment,
} from "semantic-ui-react";

const PostedJob = () => {
  const [postedJobs, setPostedJobs] = useState([]);
  const [employerId, setEmployerId] = useState(null);
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      const decoded = jwtDecode(token);
      const email = decoded.sub;

      axios
        .get(`http://localhost:9001/employee/by-email/${email}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          const id = res.data.employeeId;
          setEmployerId(id);

          axios
            .get(`http://localhost:9001/joblisting/employer/${id}`, {
              headers: { Authorization: `Bearer ${token}` },
            })
            .then((response) => setPostedJobs(response.data))
            .catch((err) => console.error("Error fetching jobs:", err));
        })
        .catch((err) => console.error("Error fetching employer:", err));
    }
  }, [token]);

  return (
    <Container style={{ marginTop: "5em", marginBottom: "3em" }}>
      <Segment raised padded>
        <Header as="h2" icon textAlign="center" color="blue">
          <Icon name="folder open" />
          Your Posted Jobs
        </Header>

        {postedJobs.length === 0 ? (
          <Message warning icon>
            <Icon name="info circle" />
            <Message.Content>
              <Message.Header>No jobs posted yet.</Message.Header>
              Post a new job from your dashboard to see it listed here.
            </Message.Content>
          </Message>
        ) : (
          <Card.Group centered stackable itemsPerRow={2}>
            {postedJobs.map((job) => (
              <Card key={job.id} color="teal" raised>
                <Card.Content>
                  <Card.Header>{job.title}</Card.Header>
                  <Card.Meta>
                    <span>{job.location}</span>
                  </Card.Meta>
                  <Card.Description>
                    {job.description}
                    <br />
                    <strong>Salary:</strong> ₹{job.salary}
                    <br />
                    <strong>Posted On:</strong> {job.postDate}
                  </Card.Description>
                </Card.Content>
                <Card.Content extra>
                  <Button
                    fluid
                    color="blue"
                    onClick={() => navigate(`/application-status/${job.id}`)}
                  >
                    <Icon name="clipboard list" />
                    View Applications
                  </Button>
                </Card.Content>
              </Card>
            ))}
          </Card.Group>
        )}
      </Segment>
    </Container>
  );
};

export default PostedJob;
