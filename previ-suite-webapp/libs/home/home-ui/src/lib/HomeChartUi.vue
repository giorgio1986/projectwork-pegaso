<script setup lang="ts">
import Chart from "primevue/chart";
import {Card} from "primevue";
import { ref, onMounted } from "vue";

onMounted(() => {
  chartData.value = setChartData();
  chartOptions.value = setChartOptions();
});

const chartData = ref();
const chartOptions = ref();

const setChartData = () => {
  const documentStyle = getComputedStyle(document.documentElement);

  return {
    labels: ['TFR', 'Aderente', 'Azienda', 'Trasferimenti', 'Anticipi', 'Riscatti'],
    datasets: [
      {
        borderColor: documentStyle.getPropertyValue('--p-cyan-500'),
        backgroundColor: "#316b48",
        data: [23567, 54256, 12987, 34825, -44278, -12043]
      }
    ]
  };
};
const setChartOptions = () => {
  const documentStyle = getComputedStyle(document.documentElement);
  const textColor = documentStyle.getPropertyValue('--p-text-color');
  const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color');
  const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color');

  return {
    maintainAspectRatio: false,
    aspectRatio: 0.8,
    plugins: {
      legend: { display: false },
    },
    scales: {
      x: {
        ticks: {
          color: textColorSecondary,
          font: {
            weight: 500
          }
        },
        grid: {
          display: false,
          drawBorder: false
        }
      },
      y: {
        ticks: {
          color: textColorSecondary
        },
        grid: {
          color: surfaceBorder,
          drawBorder: false
        }
      }
    }
  };
}
</script>

<template>
  <div class="card flex-[2]">
    <Chart type="bar" :data="chartData" :options="chartOptions" class="h-full"/>
  </div>
</template>

<style scoped>

</style>
