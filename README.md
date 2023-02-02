# java-ws-stomp

Example repo for passing trace context across stomp proto on a websocket.
This example uses Spring Boot for most components.

# introduction

Tracing in distributed systems can be challenging, and often moreso
when messaging systems are involved. To stitch together a comprehensive trace,
the [trace context](https://opentelemetry.io/docs/instrumentation/js/context/)
must be propagated between components. In HTTP systems, this is relatively
straightforward, and the W3C HTTP headers are readily propagated by OpenTelemetry
instrumentation.

Messaging systems almost always have headers too, but, depending on the implementation, 
can be tricky to implement. This is further complicated by ability of many messaging
systems to support one-to-many or even many-to-one models. The OpenTelemetry community
has built a detailed set of specifications around messaging systems that you can 
[read here](https://github.com/open-telemetry/opentelemetry-specification/blob/main/specification/trace/semantic_conventions/messaging.md).
Not every type of messaging system has been covered yet by OpenTelemetry's
autoinstrumentation.

Depending on the protocols in place, it could be even worse -- we could have 
the ability to use or see headers with each protocol frame, the envelope that
contains messages, and possibly within the message itself! Yikes.

In this little tutorial, we will  ...

# topology

TODO: describe websockets and stomp

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

<img width="612" alt="image" src="https://user-images.githubusercontent.com/75337021/216202352-18481f6b-23ee-431c-8466-80e000759665.png">


# appendix

* [related discussion](https://github.com/open-telemetry/opentelemetry-java/discussions/3345)
