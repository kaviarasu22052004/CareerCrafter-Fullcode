import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Header,
  Card,
  Button,
  Icon,
  Segment,
  Grid,
  Label,
  Loader,
  Message,
  Input,
} from "semantic-ui-react";

const JobBoard = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTitle, setSearchTitle] = useState("");
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  // Fetch all jobs on mount
  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = () => {
    setLoading(true);
    axios
      .get("http://localhost:9001/joblisting/getall", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setJobs(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching jobs:", err);
        setLoading(false);
      });
  };

  const handleApplyClick = (jobId) => {
    navigate(`/apply/${jobId}`);
  };

  const handleSearch = () => {
    if (!searchTitle.trim()) {
      fetchJobs(); // If empty, reset to all jobs
      return;
    }

    setLoading(true);
    axios
      .get(`http://localhost:9001/joblisting/search/${searchTitle}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setJobs(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error searching jobs:", err);
        setJobs([]); // Show empty result
        setLoading(false);
      });
  };

  return (
    <div
      style={{
        background: "#f9fafc",
        minHeight: "100vh",
        paddingTop: "6em",
        paddingBottom: "2em",
      }}
    >
      <Container>
        <Header as="h2" textAlign="center" style={{ color: "#2c3e50", marginBottom: "1em" }}>
          <Icon name="suitcase" color="blue" />
          <Header.Content>
            Explore Job Opportunities
            <Header.Subheader style={{ color: "#555", fontSize: "1rem", marginTop: "0.5em" }}>
              Discover openings that match your skills and experience.
            </Header.Subheader>
          </Header.Content>
        </Header>

        <Input
          icon="search"
          placeholder="Search by job title..."
          value={searchTitle}
          onChange={(e) => setSearchTitle(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSearch()}
          action={{
            icon: "search",
            onClick: handleSearch,
            color: "blue",
          }}
          fluid
          style={{ marginBottom: "2em" }}
        />

        {loading ? (
          <Segment basic>
            <Loader active inline="centered" size="large" content="Loading jobs..." />
          </Segment>
        ) : jobs.length === 0 ? (
          <Message info icon>
            <Icon name="info circle" />
            <Message.Content>
              <Message.Header>No Jobs Found</Message.Header>
              Try a different title or check back later.
            </Message.Content>
          </Message>
        ) : (
          <Grid stackable columns={3} doubling>
            {jobs.map((job) => (
              <Grid.Column key={job.id}>
                <Card fluid raised style={{ boxShadow: "0 4px 12px rgba(0,0,0,0.05)" }}>
                  <Card.Content>
                    <Header as="h3" style={{ marginBottom: "0.5em", color: "#2c3e50" }}>
                      {job.title}
                    </Header>
                    <Label basic color="teal" size="small" style={{ marginBottom: "0.8em" }}>
                      <Icon name="map marker alternate" /> {job.location}
                    </Label>

                    <Card.Description style={{ fontSize: "0.95rem", color: "#444" }}>
                      {job.description.length > 150
                        ? job.description.substring(0, 150) + "..."
                        : job.description}
                    </Card.Description>
                  </Card.Content>

                  <Card.Content extra>
                    <Label size="medium" color="green" ribbon>
                      ₹{job.salary}
                    </Label>

                    <Button
                      floated="right"
                      color="blue"
                      size="small"
                      onClick={() => handleApplyClick(job.id)}
                    >
                      <Icon name="paper plane" />
                      Apply Now
                    </Button>
                  </Card.Content>
                </Card>
              </Grid.Column>
            ))}
          </Grid>
        )}
      </Container>
    </div>
  );
};

export default JobBoard;
