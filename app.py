import sys
import pprint
import google.generativeai as palm

key=sys.argv[3]
palm.configure(api_key=key)

models = [m for m in palm.list_models() if 'generateText' in m.supported_generation_methods]
model = models[0].name
prompt = sys.argv[1]
guidance=sys.argv[2]

completion = palm.generate_text(
    model=model,
    prompt=prompt+":"+guidance,
    temperature=0,
    # maximum length of response
    # max_output_tokens=800,
)
print(completion.result)