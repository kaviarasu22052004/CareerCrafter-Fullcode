import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import {
  Container,
  Header,
  Table,
  Button,
  Icon,
  Segment,
  Message,
  Label,
} from "semantic-ui-react";

const ApplicationApproval = () => {
  const { jobListingId: urlListingId } = useParams();
  const jobListingId = urlListingId;
  const [applications, setApplications] = useState([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!jobListingId) return;

    axios
      .get(`http://localhost:9001/application/getByJobListing/${jobListingId}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setApplications(res.data))
      .catch((err) => console.error("Failed to fetch applications:", err));
  }, [jobListingId, token]);

  const updateStatus = (id, status) => {
    axios
      .put(
        `http://localhost:9001/application/updateStatus/${id}`,
        { status },
        { headers: { Authorization: `Bearer ${token}` } }
      )
      .then(() => {
        alert(`Application ${status.toLowerCase()} successfully`);
        setApplications((prev) =>
          prev.map((app) => (app.id === id ? { ...app, status } : app))
        );
      })
      .catch((err) => console.error("Status update failed:", err));
  };

  const getStatusLabel = (status) => {
    switch (status) {
      case "APPLIED":
        return <Label color="blue">Applied</Label>;
      case "SELECTED":
        return <Label color="green">Selected</Label>;
      case "REJECTED":
        return <Label color="red">Rejected</Label>;
      default:
        return <Label>{status}</Label>;
    }
  };

  return (
    <Container style={{ marginTop: "4em", marginBottom: "3em" }}>
      <Segment raised padded>
        <Header as="h2" icon textAlign="center" color="blue">
          <Icon name="users" />
          Applications for Job ID: {jobListingId}
        </Header>

        {applications.length === 0 ? (
          <Message warning icon>
            <Icon name="file outline" />
            <Message.Content>
              <Message.Header>No applications found.</Message.Header>
              This job has not received any applications yet.
            </Message.Content>
          </Message>
        ) : (
          <Table celled striped selectable>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Application ID</Table.HeaderCell>
                <Table.HeaderCell>Job Seeker ID</Table.HeaderCell>
                <Table.HeaderCell>Resume ID</Table.HeaderCell>
                <Table.HeaderCell>Status</Table.HeaderCell>
                <Table.HeaderCell>Applied Date</Table.HeaderCell>
                <Table.HeaderCell textAlign="center">Actions</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              {applications.map((app) => (
                <Table.Row key={app.id}>
                  <Table.Cell>{app.id}</Table.Cell>
                  <Table.Cell>{app.jobSeekerId}</Table.Cell>
                  <Table.Cell>{app.resumeId}</Table.Cell>
                  <Table.Cell>{getStatusLabel(app.status)}</Table.Cell>
                  <Table.Cell>{app.appliedDate}</Table.Cell>
                  <Table.Cell textAlign="center">
                    <Button
                      color="green"
                      size="small"
                      disabled={app.status === "SELECTED"}
                      onClick={() => updateStatus(app.id, "SELECTED")}
                    >
                      <Icon name="check" /> Approve
                    </Button>
                    <Button
                      color="red"
                      size="small"
                      disabled={app.status === "REJECTED"}
                      onClick={() => updateStatus(app.id, "REJECTED")}
                    >
                      <Icon name="cancel" /> Reject
                    </Button>
                  </Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table>
        )}
      </Segment>
    </Container>
  );
};

export default ApplicationApproval;
