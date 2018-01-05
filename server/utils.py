import json


def log(message):
    #print("### " + str(message))
    pass


def from_json(item):
    item = item.replace("'", '"')
    return json.loads(item)


def to_json(item):
    content = json.dumps(item, sort_keys=True, indent=2, separators=(',', ': '));
    return content
