# Yolov8 Server for detection objects.

- Java 10+
- [YoloV8](https://github.com/ultralytics/ultralytics) pretrained model.
- Open source [ai.djl ](https://djl.ai/) library to build and deploy DL in Java.
- [Spring Boot](https://spring.io/projects/spring-boot) for creating REST API application.
- [Docker container](https://www.docker.com/resources/what-container/).
## <div align="center">REST API</div>


#### Detection objects on image

<details>
  <summary><code>POST</code> <code><b>/{api/detection/detect}</b></code> <code>(detect objects on image using yolov8 pretrained model)</code></summary>

##### Parameters

> | name    |  type     | data type | description                          |
> |---------|-----------|-----------|--------------------------------------|
> | `image` |  required | string    | The image file path in your local fs |

##### Responses

> | http code | content-type                      | response                                                                                                                                      |
> |-----------|-----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8`        | `{"detected": json with detected objects and their classnames and probabilities, "base64Image": base64 encoded image with detected objects}"` |
> | `400`     | `application/json`                | `{"code":"400","message":"Bad Request"}`                                                                                                      | |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:8080
> ```

</details>

#### Helper

<details>
  <summary><code>GET</code> <code><b>/{api-docs}</b></code> <code>(REST API documentation)</code></summary>

##### Parameters

> | name  |  type     | data type             | description |
> |-------|-----------|-----------------------|-------------|
> | None  |  required | object (JSON or YAML) | N/A         |

##### Responses

> | http code | content-type                      | response                                 |
> |-----------|-----------------------------------|------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8`        | `Json with documented REST API`          |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:8080
> ```

</details>

## <div align="center">Documentation</div>

See below for a quickstart installation and usage example.

<details open>
<summary>Install</summary>

1. You need to have or [install](https://docs.docker.com/engine/install/) Docker Engine.
2. Clone this repository:
    ```bash
    git clone git@github.com:SheepIsland/YOLOv8.git
    ```
3. Build the Docker image:

   Open a terminal or command prompt, navigate to the root directory of yolov8 project (where the Dockerfile is located), and run the following command:
     ```bash
    docker build -t yolov8 . --platform linux/amd64
    ```
4.  Run the Docker container: 
    
    After the Docker image is built successfully, you can run a container from it using the following command:
    ```bash
     docker run --platform linux/amd64 -p 8080:8080 yolov8
    ```

</details>

<details open>
<summary>Usage</summary>

1. Via REST API or
2. Web UI: http://localhost:8080.

![yolov8](yolov8.png)
</details>


