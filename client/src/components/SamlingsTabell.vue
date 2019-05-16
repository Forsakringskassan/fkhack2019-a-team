<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-data-table :headers="headers" :items="Person" class="elevation-1">
        <template v-slot:items="props">
            <td>{{ props.item.namn }}</td>
            <td v-for="dag in props.item.dagar" :key="dag">{{dag}}</td>
        </template>
    </v-data-table>
</template>


<script>
    export default {
        data() {
            return {
                headers: [
                    {text: 'Namn', value: 'name'},
                    {text: '1', value: 'Day1'},
                    {text: '2', value: 'Day2'},
                    {text: '3', value: 'Day3'},
                    {text: '4', value: 'Day4'},
                    {text: '6', value: 'Day5'},
                    {text: '7', value: 'Day6'},
                    {text: '8', value: 'Day7'},
                    {text: '9', value: 'Day8'},
                    {text: '10', value: 'Day9'},
                    {text: '11', value: 'Day10'},
                    {text: '12', value: 'Day11'},
                    {text: '13', value: 'Day12'},
                    {text: '15', value: 'Day13'},
                    {text: '16', value: 'Day14'},
                    {text: '17', value: 'Day15'},
                    {text: '18', value: 'Day16'},
                    {text: '19', value: 'Day17'},
                    {text: '20', value: 'Day20'},
                    {text: '21', value: 'Day21'},
                    {text: '22', value: 'Day22'},
                    {text: '23', value: 'Day23'},
                    {text: '24', value: 'Day24'},
                    {text: '25', value: 'Day25'},
                    {text: '26', value: 'Day26'},
                    {text: '27', value: 'Day27'},
                    {text: '28', value: 'Day28'},
                    {text: '29', value: 'Day29'},
                    {text: '30', value: 'Day30'},
                    {text: '31', value: 'Day31'},

                ],
                Person: [
                    {
                        namn: 'Test Testorsson',
                        kortid: '88880001',
                        dagar: [
                            '1',
                            '2',
                            '3',
                            '4',
                            '6',
                            '7',
                            '8',
                            '9',
                            '10',
                            '11',
                            '12',
                            '13',
                            '15',
                            '16',
                            '17',
                            '18',
                            '19',
                            '20',
                            '21',
                            '22',
                            '23',
                            '24',
                            '25',
                            '26',
                            '27',
                            '28',
                            '29',
                            '30',
                            '31',
                            ]
                    }
                ]
            }
        }
    }
</script>