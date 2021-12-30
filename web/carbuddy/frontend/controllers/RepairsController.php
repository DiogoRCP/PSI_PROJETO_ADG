<?php

namespace frontend\controllers;

use backend\models\Contributors;
use backend\models\Users;
use common\models\User;
use frontend\models\Cars;
use frontend\models\Repairs;
use frontend\models\RepairsSearch;
use Yii;
use yii\db\IntegrityException;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * RepairsController implements the CRUD actions for Repairs model.
 */
class RepairsController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all Repairs models.
     * @return mixed
     */
    public function actionIndex()
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $searchModel = new RepairsSearch();
            $dataProvider = $searchModel->search($this->request->queryParams);

            return $this->render('index', [
                'searchModel' => $searchModel,
                'dataProvider' => $dataProvider,
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Displays a single Repairs model.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        if (Yii::$app->user->can('frontendReadRepair')) {
            $model = $this->findModel($id);
            $contributor = Contributors::find()->where("userId = " . Yii::$app->user->getId())->one();
            if($contributor->id === $model->contributorId) {
                return $this->render('view', [
                    'model' => $this->findModel($id),
                ]);
            }
            else {
                Yii::$app->user->logout();
                return $this->goHome();
            }
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    public function actionHistory($car)
    {
        if (Yii::$app->user->can('frontendReadRepair')) {
            $searchModel = new RepairsSearch();
            $dataProvider = $searchModel->search($this->request->queryParams);

            return $this->render('index', [
                'searchModel' => $searchModel,
                'dataProvider' => $dataProvider,
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Creates a new Repairs model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $model = new Repairs();
            $modelCars = Cars::find()->all();


            $user = Users::findOne(\Yii::$app->user->getId());

                $collaborator = Contributors::find()
                    ->where(['userId' => $user->id])
                    ->one();

            $model->contributorId = $collaborator->id;

            if ($this->request->isPost) {
                if ($model->load($this->request->post()) && $model->save()) {
                    return $this->redirect(['view', 'id' => $model->id]);
                }
            } else {
                $model->loadDefaultValues();
            }

            return $this->render('create', [
                'model' => $model, 'modelCars' => $modelCars,
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Updates an existing Repairs model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $model = $this->findModel($id);
            $contributor = Contributors::find()->where("userId = " . Yii::$app->user->getId())->one();
            $modelCars = Cars::find()->all();

            if($contributor->id === $model->contributorId) {
                if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
                    return $this->redirect(['view', 'id' => $model->id]);
                }

                return $this->render('update', [
                    'model' => $model, 'modelCars' => $modelCars,
                ]);
            }
            else {
                Yii::$app->user->logout();
                return $this->goHome();
            }
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Deletes an existing Repairs model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            try {
                $this->findModel($id)->delete();
            } catch(IntegrityException $e) {
                Yii::$app->session->setFlash('error', 'You can´t delete this Repair');
            }
            return $this->redirect(['index']);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Finds the Repairs model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Repairs the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Repairs::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
