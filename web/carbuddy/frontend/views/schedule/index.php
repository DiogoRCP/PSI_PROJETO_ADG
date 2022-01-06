<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\SchedulesSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Schedules Manager';
?>
<div class="schedules-index">
    <h1><?= Html::encode($this->title) ?></h1>


    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            //'id',
            //'carId',
            //'companyId',
            //'currentdate',
            'schedulingdate',
            'repairdescription',
            'state',
            'repairtype',
            [   'label' => 'Car',
                'class' => 'yii\grid\DataColumn', // can be omitted, as it is the default
                'value' => function ($data) {
                    return $data->car->registration; // $data['name'] for array data, e.g. using SqlDataProvider.
                },
            ],
            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
