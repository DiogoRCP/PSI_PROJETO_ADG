<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\SchedulesSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Schedules';

?>
<div class="schedules-index">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Schedule', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [

            //'id',
            'carId',
            'companyId',
            'currentdate',
            'schedulingdate',
            'repairdescription',
            'state',
            'repairtype',


            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
