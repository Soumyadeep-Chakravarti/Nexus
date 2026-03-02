# Dummy Python adapter example

def send_to_nexus(msg):
    print(f"Python Adapter sending: {msg}")

def receive_from_nexus():
    return "Message from Nexus to Python"

if __name__ == "__main__":
    send_to_nexus("Hello from Python Adapter")
    print(f"Python Adapter received: {receive_from_nexus()}")
