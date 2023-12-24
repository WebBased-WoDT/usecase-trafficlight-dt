# Use case - Traffic Light Digital Twin

![Release](https://github.com/WebBased-WoDT/usecase-trafficlight-dt/actions/workflows/build-and-deploy.yml/badge.svg?style=plastic)
[![License: Apache License](https://img.shields.io/badge/License-Apache_License_2.0-yellow.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![Version](https://img.shields.io/github/v/release/WebBased-WoDT/usecase-trafficlight-dt?style=plastic)

# Usage
You need to specify the following environment variable:
- `EXPOSED_PORT`: the port used to expose the Digital Twin
- `PHYSICAL_ASSET_ID`: the physical asset identifier of the mirrored physical asset
- `PLATFORM_URL`: the platform to which the Digital Twin should register at startup

If you want to run it via docker container:
1. Provide a `.env` file with all the environment variable described above
2. Run the container with the command:
   ```bash
    docker run ghcr.io/webbased-wodt/usecase-trafficlight-dt:latest
    ```
    1. If you want to pass an environment file whose name is different from `.env` use the `--env-file <name>` parameter.