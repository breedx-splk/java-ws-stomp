# java-ws-stomp

Example repo for passing trace context across stomp proto on a websocket.
This example uses Spring Boot for most components.

# topology

```mermaid
flowchart 
    id1([WsPublisher])-->|ExampleMessage|id2[ws server]-->|TimestampedMessage|id3([WsSubscriber])
```

1. The `WsPublisher` connects to the websocket and publishes/sends
JSON messages in [stomp](https://stomp.github.io/) format to `/app/tube`.
2. The `WsServerController` contains a message mapping that converts 
`ExampleMessages` into `TimestampedMessages` and sends these to `/topic/messages`.
3. The `WsSubscriber` also connects to the ws and creates a subscription
to `/topic/messages`. When it receives a message, it logs the content.

# traces

<img width="650" alt="image" src="https://user-images.githubusercontent.com/75337021/216201486-eda10f36-a33b-4315-aca9-cd9768c1c49e.png">

<img width="610" alt="image" src="https://user-images.githubusercontent.com/75337021/216202081-452f314d-936f-45dd-bf3c-29ea0fa969df.png">


# appendix

* [related discussion](https://github.com/open-telemetry/opentelemetry-java/discussions/3345)
