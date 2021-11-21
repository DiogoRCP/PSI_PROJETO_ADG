<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\RepairsSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Repairs';

//$this->params['breadcrumbs'][] = $this->title;
?>
<div class="repairs-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Back', "javascript:history.back()", ['class' => 'btn btn-light']) ?>

        <?php if (Yii::$app->user->can('frontendCRUDRepair')) { ?>

            <?= Html::a('Create Repairs', ['create'], ['class' => 'btn btn-secondary']) ?>

        <?php } ?>
    </p>
    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>


    <?php if (\Yii::$app->request->get('car') != null) { ?>
        <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'filterModel' => $searchModel,
            'columns' => [
                'kilometers',
                'repairdate',
                'repairdescription',
                'state',
                'repairtype',
            ],
        ]); ?>
    <?php } else { ?>
        <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'filterModel' => $searchModel,
            'columns' => [
                //['class' => 'yii\grid\SerialColumn'],

                //'id',
                'kilometers',
                'repairdate',
                'repairdescription',
                'state',
                'repairtype',
                'carId',
                //'contributorId',

                ['class' => 'yii\grid\ActionColumn'],
            ],
        ]);
    } ?>

</div>
