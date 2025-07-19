// src/Component/JobSeeker/JobSeekerApplicationStatus.js
import React, { useEffect, useState } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import {
  Container,
  Header,
  Segment,
  Table,
  Loader,
  Message,
  Icon,
} from "semantic-ui-react";

const JobSeekerApplicationStatus = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const decoded = jwtDecode(token);
        const email = decoded?.sub;
        if (!email) throw new Error("Email not found in token");

        // Step 1: Get Job Seeker by email
        const seekerRes = await axios.get(
          `http://localhost:9001/jobseeker/by-email/${email}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        const jobSeekerId = seekerRes.data.jobSeekerId;

        // Step 2: Get Applications
        const appsRes = await axios.get(
          `http://localhost:9001/application/getByJobSeeker/${jobSeekerId}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        setApplications(appsRes.data);
      } catch (err) {
        console.error("Error loading applications:", err);
      } finally {
        setLoading(false);
      }
    };

    if (token) fetchApplications();
  }, [token]);

  return (
    <div style={{ background: "#f9fafc", minHeight: "100vh", paddingTop: "6em" }}>
      <Container>
        <Segment padded="very" raised>
          <Header as="h2" icon textAlign="center" color="blue">
            <Icon name="clipboard check" circular />
            <Header.Content>Application Status</Header.Content>
            <Header.Subheader>Track all the jobs you've applied to</Header.Subheader>
          </Header>

          {loading ? (
            <Loader active inline="centered" content="Loading applications..." />
          ) : applications.length === 0 ? (
            <Message info icon>
              <Icon name="info circle" />
              <Message.Content>
                <Message.Header>No Applications Found</Message.Header>
                You haven't applied for any jobs yet.
              </Message.Content>
            </Message>
          ) : (
            <Table celled striped selectable>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell>Application ID</Table.HeaderCell>
                  <Table.HeaderCell>Job Listing ID</Table.HeaderCell>
                  <Table.HeaderCell>Resume ID</Table.HeaderCell>
                  <Table.HeaderCell>Status</Table.HeaderCell>
                  <Table.HeaderCell>Applied Date</Table.HeaderCell>
                </Table.Row>
              </Table.Header>

              <Table.Body>
                {applications.map((app) => (
                  <Table.Row key={app.id}>
                    <Table.Cell>{app.id}</Table.Cell>
                    <Table.Cell>{app.jobListingId}</Table.Cell>
                    <Table.Cell>{app.resumeId}</Table.Cell>
                    <Table.Cell>
                      <Icon
                        name={
                          app.status === "APPLIED"
                            ? "hourglass half"
                            : app.status === "REJECTED"
                            ? "times circle"
                            : "check circle"
                        }
                        color={
                          app.status === "APPLIED"
                            ? "yellow"
                            : app.status === "REJECTED"
                            ? "red"
                            : "green"
                        }
                      />
                      {app.status}
                    </Table.Cell>
                    <Table.Cell>{app.appliedDate}</Table.Cell>
                  </Table.Row>
                ))}
              </Table.Body>
            </Table>
          )}
        </Segment>
      </Container>
    </div>
  );
};

export default JobSeekerApplicationStatus;
