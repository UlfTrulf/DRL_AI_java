import math
import itertools

def generate_building_tiles(num_building_types):
    building_tiles = []

    for building_type in range(1, num_building_types + 1):
        num_buildings = 5 + math.floor(building_type / 2) * 2
        costs = list(range(1 + building_type, 9 + building_type))

        wall_configs = generate_wall_configs()

        for cost, wall in itertools.product(costs, wall_configs):
            building_tiles.append((cost, building_type, wall))

    return building_tiles

def generate_wall_configs():
    valid_wall_configs = []

    for i in range(1, 16):
        binary = format(i, '04b')
        if '10' not in binary and '001' not in binary:
            valid_wall_configs.append(int(binary, 2))

    return valid_wall_configs

building_tiles = generate_building_tiles(6)
print(building_tiles)