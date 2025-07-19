import React from 'react';
import { Link } from 'react-router-dom';
import {
  Container,
  Header,
  Segment,
  Button,
  Icon,
  Menu,
  Divider,
} from 'semantic-ui-react';

const Home = () => {
  return (
    <div style={{ minHeight: '100vh', background: 'linear-gradient(to right, #dbeafe, #eff6ff)' }}>
      <Menu fixed="top" inverted>
        <Container>
          <Menu.Item header>
            <Icon name="briefcase" />
            CareerCrafter
          </Menu.Item>
          <Menu.Item as="a" href="#home">Home</Menu.Item>
          <Menu.Item as="a" href="#about">About Us</Menu.Item>
          <Menu.Item as="a" href="#help">Help</Menu.Item>
        </Container>
      </Menu>

      <Container textAlign="center" style={{ paddingTop: '7em', paddingBottom: '4em' }}>
        <Segment padded raised style={{ maxWidth: '650px', margin: 'auto' }}>
          <Header as="h1" icon textAlign="center" color="blue">
            <Icon name="briefcase" circular />
            <Header.Content>Welcome to CareerCrafter</Header.Content>
          </Header>

          <p style={{ marginTop: '2em' }}>
            <Button as={Link} to="/signup" primary size="large">
              <Icon name="signup" /> Sign Up
            </Button>
            <Button as={Link} to="/login" secondary size="large" style={{ marginLeft: '1em' }}>
              <Icon name="sign-in" /> Login
            </Button>
          </p>
        </Segment>

        <Divider hidden />

        <div id="about" style={{ marginTop: '5em' }}>
          <Header as="h2" icon textAlign="center">
            <Icon name="info circle" />
            About Us
          </Header>
          <Container text style={{ fontSize: '1.2em', marginTop: '1em' }}>
            CareerCrafter is a smart and user-friendly job portal that helps job seekers connect
            with top companies. Our platform allows users to create powerful resumes, explore job
            listings, and track their applications all in one place.
          </Container>
        </div>

        <Divider hidden />

        <div id="help" style={{ marginTop: '4em' }}>
          <Header as="h2" icon textAlign="center">
            <Icon name="help circle" />
            Help & Support
          </Header>
          <Container text style={{ fontSize: '1.2em', marginTop: '1em' }}>
            If you face any issues while signing up or using the portal, feel free to contact our
            support team. Visit the FAQ section or email us directly at support@careercrafter.com.
          </Container>
        </div>
      </Container>
    </div>
  );
};

export default Home;
